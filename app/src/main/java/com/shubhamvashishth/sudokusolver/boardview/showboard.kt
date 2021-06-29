package com.shubhamvashishth.sudokusolver.boardview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.shubhamvashishth.sudokusolver.verifyorfill
import kotlin.math.min



class showboard (context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {

    private var sqrtSize = 3
    private var size = 9
    private var listener: showboard.OnTouchListener? = null


    // these are set in onDraw
    private var cellSizePixels = 0F
    private var noteSizePixels = 0F

    public companion object opens{
    var selectedRow = 0
    var selectedCol = 0

    }

    private var cells: List<Cell>? = useClass.sugrid

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(sizePixels, sizePixels)
    }

    private val thickLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 6F
    }

    private val thinLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 2F
    }
    private val selectedCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#6ead3a")
    }

    private val conflictingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#efedef")
    }

    private val textPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
    }

    private val wrongPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.RED
    }

    private val startingCellTextPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        typeface = Typeface.DEFAULT_BOLD
    }

    private val noteTextPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
    }

    private val startingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        //color = Color.parseColor("#acacac")
        color = Color.parseColor("#ffffff")
    }



   override fun onDraw(canvas: Canvas) {
        updateMeasurements(width)
       fillCells(canvas)
        drawLines(canvas)
      drawText(canvas)
    }

    private fun drawText(canvas: Canvas) {
        cells?.forEach { cell ->
            val value = cell.value

            if (value == null) {
                // draw notes
                cell.notes.forEach { note ->
                    val rowInCell = (note - 1) / sqrtSize
                    val colInCell = (note - 1) % sqrtSize
                    val valueString = note.toString()

                    val textBounds = Rect()
                    noteTextPaint.getTextBounds(valueString, 0, valueString.length, textBounds)
                    val textWidth = noteTextPaint.measureText(valueString)
                    val textHeight = textBounds.height()
                    canvas.drawText(
                        valueString,
                        (cell.col * cellSizePixels) + (colInCell * noteSizePixels) + noteSizePixels / 2 - textWidth / 2f,
                        (cell.row * cellSizePixels) + (rowInCell * noteSizePixels) + noteSizePixels / 2 + textHeight / 2f,
                        noteTextPaint
                    )
                }
            } else {
                val row = cell.row
                val col = cell.col
                val valueString = if(cell.value== null) "" else cell.value.toString()


              var paintToUse = if (cell.isStartingCell) startingCellTextPaint else textPaint

                if(cell.isStartingCell) paintToUse.apply { color= Color.BLACK }
                if(cell.isWrong) paintToUse.apply { color= Color.RED }
                else paintToUse.apply { color= Color.BLACK }

               /* var paintToUse: Paint
                if(cell.isStartingCell){paintToUse= startingCellTextPaint}
                else if(cell.isWrong){paintToUse= wrongPaint}
                else paintToUse= textPaint*/

                val textBounds = Rect()
                paintToUse.getTextBounds(valueString, 0, valueString.length, textBounds)
                val textWidth = paintToUse.measureText(valueString)
                val textHeight = textBounds.height()

                canvas.drawText(valueString, (col * cellSizePixels) + cellSizePixels / 2 - textWidth / 2,
                    (row * cellSizePixels) + cellSizePixels / 2 + textHeight / 2, paintToUse)
            }
        }
    }

    private fun fillCells(canvas: Canvas) {
        cells?.forEach {
            val r = it.row
            val c = it.col

            if (it.isStartingCell) {
                fillCell(canvas, r, c, startingCellPaint)
            } else if (r == selectedRow && c == selectedCol) {
                fillCell(canvas, r, c, selectedCellPaint)
            } else if (r == selectedRow || c == selectedCol) {
                fillCell(canvas, r, c, conflictingCellPaint)
            } else if (r / sqrtSize == selectedRow / sqrtSize && c / sqrtSize == selectedCol / sqrtSize) {
                fillCell(canvas, r, c, conflictingCellPaint)
            }
        }
    }

    private fun fillCell(canvas: Canvas, r: Int, c: Int, paint: Paint) {
        canvas.drawRect(c * cellSizePixels, r * cellSizePixels, (c + 1) * cellSizePixels, (r + 1) * cellSizePixels, paint)
    }

    private fun updateMeasurements(width: Int) {
        cellSizePixels = width / size.toFloat()
        noteSizePixels = cellSizePixels / sqrtSize.toFloat()
        noteTextPaint.textSize = cellSizePixels / sqrtSize.toFloat()
        textPaint.textSize = cellSizePixels / 1.5F
        startingCellTextPaint.textSize = cellSizePixels / 1.5F
    }

    private fun drawLines(canvas: Canvas) {
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), thickLinePaint)

        for (i in 0 until size) {
            val paintToUse = when (i % sqrtSize) {
                0 -> thickLinePaint
                else -> thinLinePaint
            }

            canvas.drawLine(
                i * cellSizePixels,
                0F,
                i * cellSizePixels,
                height.toFloat(),
                paintToUse
            )

            canvas.drawLine(
                0F,
                i * cellSizePixels,
                width.toFloat(),
                i * cellSizePixels,
                paintToUse
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                handleTouchEvent(event.x, event.y)
                true
            }
            else -> false
        }
    }

    private fun handleTouchEvent(x: Float, y: Float) {
        val possibleSelectedRow = (y / cellSizePixels).toInt()
        val possibleSelectedCol = (x / cellSizePixels).toInt()
        listener?.onCellTouched(possibleSelectedRow, possibleSelectedCol)
        updateSelectedCellUI(possibleSelectedRow, possibleSelectedCol)
    }

    fun updateSelectedCellUI(row: Int, col: Int) {
        selectedRow = row
        selectedCol = col
        updateCells(cells)
        invalidate()
    }

    fun updateCells(cells: List<Cell>?) {
        this.cells = useClass.sugrid
        invalidate()
    }

    fun registerListener(listener: showboard.OnTouchListener) {
        this.listener = listener
    }

    interface OnTouchListener {
        fun onCellTouched(row: Int, col: Int)
    }

    fun clear(){
        invalidate()
    }



}
public class Cell(
    val row: Int,
    val col: Int,
    var value: Int?,
    var isStartingCell: Boolean = false,
    var isWrong: Boolean= false,
    var notes: MutableSet<Int> = mutableSetOf<Int>()
)


