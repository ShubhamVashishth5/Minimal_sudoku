package com.shubhamvashishth.sudokusolver

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.shubhamvashishth.sudokusolver.algo.solveinjava
import com.shubhamvashishth.sudokusolver.boardview.showboard
import com.shubhamvashishth.sudokusolver.boardview.useClass
import com.shubhamvashishth.sudokusolver.generator.Board
import com.shubhamvashishth.sudokusolver.generator.Generator
import com.shubhamvashishth.sudokusolver.algo.check
import com.shubhamvashishth.sudokusolver.algo.wrongFiller
import com.shubhamvashishth.sudokusolver.boardview.Cell
//import org.junit.rules.Timeout.seconds

import android.os.Handler
import java.util.*
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

import android.content.SharedPreferences
import com.shubhamvashishth.sudokusolver.algo.saveObjectToSharedPreference


class playSudoku : AppCompatActivity() {

    lateinit var shw: showboard
    lateinit var txt: TextView
    var running= true
    var runtime= true
    var noteon=false
    var activityisrunning=true




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_sudoku)

        var mAddFab = findViewById<View>(R.id.add_fab)
       var  mAddAlarmFab = findViewById<FloatingActionButton>(R.id.add_alarm_fab)
        var mAddPersonFab = findViewById<FloatingActionButton>(R.id.add_person_fab)
       var addAlarmActionText = findViewById<View>(R.id.add_alarm_action_text)
        var addPersonActionText = findViewById<View>(R.id.add_person_action_text)
        var begone9=findViewById<Button>(R.id.nineButton)
        var begonesub=findViewById<Button>(R.id.submit)

        mAddAlarmFab.setVisibility(View.GONE)
        mAddPersonFab.setVisibility(View.GONE)
        addAlarmActionText.setVisibility(View.GONE)
        addPersonActionText.setVisibility(View.GONE)


       var  isAllFabsVisible = false


        mAddFab.setOnClickListener(
            View.OnClickListener {
                if (!isAllFabsVisible) {

                    mAddAlarmFab.show()
                    mAddPersonFab.show()
                    addAlarmActionText.setVisibility(View.VISIBLE)
                    addPersonActionText.setVisibility(View.VISIBLE)

                    isAllFabsVisible = true
                } else {


                    mAddAlarmFab.hide()
                    mAddPersonFab.hide()
                    addAlarmActionText.setVisibility(View.GONE)
                    addPersonActionText.setVisibility(View.GONE)
                    begone9.visibility=View.VISIBLE
                    begonesub.visibility=View.VISIBLE
                    isAllFabsVisible = false
                }
            })

        mAddPersonFab.setOnClickListener(
            View.OnClickListener {
               if(runtime)
                Toast.makeText(
                    this,
                    "Timer paused",
                    Toast.LENGTH_SHORT
                ).show()
                else
                   Toast.makeText(
                       this,
                       "Timer resumed",
                       Toast.LENGTH_SHORT
                   ).show()
                runtime = !runtime

            })


        mAddAlarmFab.setOnClickListener(
            View.OnClickListener {
                var tosolve: solveinjava = solveinjava()

                useClass.equal(useClass.ugrid,useClass.sugrid)
                useClass.equal(useClass.sugrid, useClass.qgrid)


                var new: List<Cell> = useClass.sugrid
                tosolve.getBoard(useClass.sugrid)
                tosolve.solveSudoku(tosolve.board,9)
                tosolve.setBoard(useClass.sugrid)
                runtime=false

                val intent = Intent(this, solvedsudoku::class.java)
                startActivity(intent)
            })

        txt =findViewById(R.id.done)

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
        findViewById<Button>(R.id.notes).setOnClickListener(clickListener)
        findViewById<Button>(R.id.submit).setOnClickListener(clickListener)
        findViewById<Button>(R.id.noval).setOnClickListener(clickListener)




    }

    val clickListener = View.OnClickListener { view ->
        var curRow= showboard.selectedRow
        var curCol = showboard.selectedCol
        var index= curRow*9 + curCol
        when (view.id) {
            R.id.oneButton ->{ if(!useClass.sugrid[index].isStartingCell){
                useClass.sugrid[index].value= 1
                if(noteon){ useClass.sugrid[index].value= null
                    if(useClass.sugrid[index].notes.contains(1))useClass.sugrid[index].notes.remove(1)
                    else(useClass.sugrid[index].notes.add(1))
                }}
                useClass.setfalse()
                shw.clear()}

            R.id.twoButton -> {if(!useClass.sugrid[index].isStartingCell){
                useClass.sugrid[index].value= 2
                if(noteon){ useClass.sugrid[index].value= null
                    if(useClass.sugrid[index].notes.contains(2))useClass.sugrid[index].notes.remove(2)
                    else(useClass.sugrid[index].notes.add(2))
                }}
                useClass.setfalse()
                shw.clear()}

            R.id.threeButton -> {if(!useClass.sugrid[index].isStartingCell){
                useClass.sugrid[index].value= 3
                if(noteon){ useClass.sugrid[index].value= null
                    if(useClass.sugrid[index].notes.contains(3))useClass.sugrid[index].notes.remove(3)
                    else(useClass.sugrid[index].notes.add(3))
                }}
                useClass.setfalse()
                shw.clear()}
            R.id.fourButton ->{ if(!useClass.sugrid[index].isStartingCell){
                useClass.sugrid[index].value= 4
                if(noteon){ useClass.sugrid[index].value= null
                    if(useClass.sugrid[index].notes.contains(4))useClass.sugrid[index].notes.remove(4)
                    else(useClass.sugrid[index].notes.add(4))
                }}
                useClass.setfalse()
                shw.clear()}
            R.id.fiveButton -> { if(!useClass.sugrid[index].isStartingCell){
                useClass.sugrid[index].value= 5
                if(noteon){ useClass.sugrid[index].value= null
                    if(useClass.sugrid[index].notes.contains(5))useClass.sugrid[index].notes.remove(5)
                    else(useClass.sugrid[index].notes.add(5))
                }}
                useClass.setfalse()
                shw.clear()}
            R.id.sixButton-> { if(!useClass.sugrid[index].isStartingCell){
                useClass.sugrid[index].value= 6
                if(noteon){ useClass.sugrid[index].value= null
                    if(useClass.sugrid[index].notes.contains(6))useClass.sugrid[index].notes.remove(6)
                    else(useClass.sugrid[index].notes.add(6))
                }}
                useClass.setfalse()
                shw.clear()}
            R.id.sevenButton -> { if(!useClass.sugrid[index].isStartingCell){
                useClass.sugrid[index].value= 7
                if(noteon){ useClass.sugrid[index].value= null
                    if(useClass.sugrid[index].notes.contains(7))useClass.sugrid[index].notes.remove(7)
                    else(useClass.sugrid[index].notes.add(7))
                }}
                useClass.setfalse()
                shw.clear()}
            R.id.eightButton -> { if(!useClass.sugrid[index].isStartingCell){
                useClass.sugrid[index].value= 8
                if(noteon){ useClass.sugrid[index].value= null
                    if(useClass.sugrid[index].notes.contains(8))useClass.sugrid[index].notes.remove(8)
                    else(useClass.sugrid[index].notes.add(8))
                }}
                useClass.setfalse()
                shw.clear()}
            R.id.nineButton -> { if(!useClass.sugrid[index].isStartingCell){
                useClass.sugrid[index].value= 9
                if(noteon){ useClass.sugrid[index].value= null
                    if(useClass.sugrid[index].notes.contains(9))useClass.sugrid[index].notes.remove(9)
                    else(useClass.sugrid[index].notes.add(9))
                }}
                useClass.setfalse()
                shw.clear()}
            R.id.noval -> { if(!useClass.sugrid[index].isStartingCell)
                useClass.sugrid[index].value= null
                useClass.sugrid[index].notes.clear()
                useClass.setfalse()
                shw.clear()


            }
            R.id.notes -> {
                var btn= findViewById<Button>(R.id.notes)
                noteon= !noteon
                if(noteon){
                    btn.setBackgroundColor(Color.GRAY)
                    btn.setTextColor(Color.WHITE)
                }
                else {
                    btn.setBackgroundColor(Color.WHITE)
                    btn.setTextColor(Color.BLACK)
                }
            }

            R.id.submit-> {

                useClass.equal(useClass.ugrid,useClass.sugrid)

                wrongFiller()
                shw.clear()


                if(check())
                {txt.setText("")
                    val intent = Intent(this, goodjob::class.java)
                startActivity(intent)}

                else
                {
                    runtime=false
                    Handler().postDelayed({
                        runtime=true
                                          useClass.setfalse()
                        shw.clear()
                                          }, 2000)

                    txt.setText("Missing or incorrect values ")}




            }





        }
    }

    override fun onResume() {
        super.onResume()
        useClass.equal(useClass.sugrid, useClass.ugrid)
        shw.clear()
        runtime= true
        activityisrunning=true
        runTimer()
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


    var seconds= useClass.timeins
    private fun runTimer() {

        val handler = Handler()

        handler.post(object : Runnable {
            override fun run() {
                if(activityisrunning){
                val hours: Int = seconds.toInt() / 3600
                val minutes: Int = seconds.toInt() % 3600 / 60
                val secs: Int = seconds.toInt() % 60


                val time: String = java.lang.String
                    .format(
                        Locale.getDefault(),
                        "%d:%02d:%02d", hours,
                        minutes, secs
                    )


                if(runtime)txt.text = time

                useClass.time=time
                useClass.timeins=seconds



                if (runtime) {
                    seconds+=0.1
                }


                handler.postDelayed(this, 100)

            }}
        })
    }

    override fun onStop() {
        super.onStop()
        runtime=false
        activityisrunning=false
        saveObjectToSharedPreference(this@playSudoku, "savedata.txt", "sugrid", useClass.sugrid )
        saveObjectToSharedPreference(this@playSudoku, "savedata.txt", "qgrid", useClass.qgrid )
        saveObjectToSharedPreference(this@playSudoku, "savedata.txt", "ugrid", useClass.sugrid )
        saveObjectToSharedPreference(this@playSudoku, "savedata.txt", "time", useClass.timeins )
    }

   override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()

    }




}