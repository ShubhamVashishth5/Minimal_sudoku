package com.shubhamvashishth.sudokusolver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.badge.BadgeUtils
import com.shubhamvashishth.sudokusolver.algo.solveinjava
import com.shubhamvashishth.sudokusolver.boardview.Cell
import com.shubhamvashishth.sudokusolver.boardview.showboard
import com.shubhamvashishth.sudokusolver.boardview.useClass
import com.shubhamvashishth.sudokusolver.generator.Board
import com.shubhamvashishth.sudokusolver.generator.Generator


class verifyorfill : AppCompatActivity() {


    lateinit var shw: showboard


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifyorfill)


        shw = findViewById(R.id.sudokuBoardView)
        findViewById<Button>(R.id.oneButton).setOnClickListener(clickListener)
        findViewById<Button>(R.id.twoButton).setOnClickListener(clickListener)
        findViewById<Button>(R.id.threeButton).setOnClickListener(clickListener)
        findViewById<Button>(R.id.fourButton).setOnClickListener(clickListener)
        findViewById<Button>(R.id.fiveButton).setOnClickListener(clickListener)
        findViewById<Button>(R.id.sixButton).setOnClickListener(clickListener)
        findViewById<Button>(R.id.sevenButton).setOnClickListener(clickListener)
        findViewById<Button>(R.id.eightButton).setOnClickListener(clickListener)
        findViewById<Button>(R.id.nineButton).setOnClickListener(clickListener)
        findViewById<Button>(R.id.solve).setOnClickListener(clickListener)
        findViewById<Button>(R.id.play).setOnClickListener(clickListener)
        findViewById<Button>(R.id.noval).setOnClickListener(clickListener)
        findViewById<Button>(R.id.clear).setOnClickListener(clickListener)





    }

    val clickListener = View.OnClickListener { view ->
       var curRow= showboard.selectedRow
        var curCol = showboard.selectedCol
        var index= curRow*9 + curCol
        when (view.id) {
          R.id.oneButton ->{ useClass.sugrid[index].value= 1
            shw.clear()}
            R.id.twoButton -> { useClass.sugrid[index].value= 2
                shw.clear()}
            R.id.threeButton -> { useClass.sugrid[index].value= 3

                shw.clear()}
            R.id.fourButton ->{ useClass.sugrid[index].value= 4

                shw.clear()}
            R.id.fiveButton -> { useClass.sugrid[index].value= 5

                shw.clear()}
            R.id.sixButton-> { useClass.sugrid[index].value= 6

                shw.clear()}
            R.id.sevenButton -> { useClass.sugrid[index].value= 7

                shw.clear()}
            R.id.eightButton -> { useClass.sugrid[index].value= 8

                shw.clear()}
            R.id.nineButton -> { useClass.sugrid[index].value= 9


                shw.clear()}

            R.id.noval-> {useClass.sugrid[index].value= null
                useClass.sugrid[index].isStartingCell= false
                shw.clear()
            }


            R.id.solve -> {
                setstart()
                var tosolve: solveinjava = solveinjava()
                tosolve.getBoard(useClass.sugrid)
                tosolve.solveSudoku(tosolve.board,9)
                tosolve.setBoard(useClass.sugrid)

                val intent = Intent(this, solvedsudoku::class.java)
                    startActivity(intent)

            }

            R.id.play-> {

                setstart()
                useClass.equal(useClass.ugrid,useClass.sugrid)
                useClass.equal(useClass.qgrid,useClass.sugrid)
                useClass.timeins= 0.0

                var intent= Intent(this,playSudoku::class.java)
                startActivity(intent)


            }

            R.id.clear->{
                useClass.cleargrid(useClass.sugrid)
                shw.clear()
            }





        }
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }


    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (//View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or   View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }


    fun setstart(){
        for(i in 0..8)
            for(j in 0..8)
                if(useClass.sugrid[i*9+j].value!=null)
                useClass.sugrid[i*9+j].isStartingCell=true

    }

    override fun onBackPressed() {
        super.onBackPressed()
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }

}

