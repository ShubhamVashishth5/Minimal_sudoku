package com.shubhamvashishth.sudokusolver

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.FileProvider
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.shubhamvashishth.sudokusolver.algo.getSavedObjectFromPreference
import com.shubhamvashishth.sudokusolver.boardview.useClass
import com.shubhamvashishth.sudokusolver.generator.Board
import com.shubhamvashishth.sudokusolver.generator.Generator
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import com.google.gson.Gson
import android.preference.PreferenceManager
import android.widget.*
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropActivity
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity(),
    AdapterView.OnItemSelectedListener {
    var level=0
    var isrunning=false
    lateinit var a: LinearProgressIndicator
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



        a= findViewById(R.id.wait)
        a.visibility= View.GONE
        val camclick: Button = findViewById(R.id.cam_button);
        val devclick: Button = findViewById(R.id.dev_button);
        val fillclick: Button= findViewById(R.id.man_button);

        var spinner: Spinner = findViewById(R.id.spinner)
      spinner.onItemSelectedListener= this



        ArrayAdapter.createFromResource(
            this,
            R.array.levels,
            android.R.layout.simple_spinner_item

        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }



        camclick.setOnClickListener(clickListener)
      devclick.setOnClickListener(clickListener)
      fillclick.setOnClickListener(clickListener)
        findViewById<Button>(R.id.play).setOnClickListener(clickListener)




    }

    val clickListener = View.OnClickListener { view ->

        when (view.id) {
           R.id.cam_button -> {
               dispatchTakePictureIntent()

            }

            R.id.man_button -> {
                useClass.cleargrid(useClass.sugrid)
                val intent = Intent(this, aboutPerson::class.java).putExtra("From" , 0)
            startActivity(intent)
            }
            R.id.dev_button -> {

                var f= File("/data/data/" + packageName + "/shared_prefs/" + "savedata.txt" + ".xml");
                //f.exists()
                if(!f.exists()){
                    Toast.makeText(
                        this,
                        "No existing game",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@OnClickListener}

                getSavedObjectFromPreference(this,"savedata.txt", "sugrid",useClass.sugrid)
                val intent = Intent(this, playSudoku::class.java)
                startActivity(intent)
            }

            R.id.play-> {

                if(isrunning) return@OnClickListener

                useClass.cleargrid(useClass.sugrid)

                a.visibility= View.VISIBLE

              isrunning=true
                Thread(Runnable {

                    var bb: Generator = Generator()
                    var board: Board = bb.generateSudoku(Generator.DIFFICULTY.values()[level])

                    useClass.setfalse()
                    board.setBoard(useClass.sugrid)
                    board.setBoard(useClass.qgrid)
                    board.setBoard(useClass.ugrid)

                    useClass.time="0"
                    useClass.timeins=0.0

                    val intent = Intent(this, playSudoku::class.java)
                    startActivity(intent)
                    isrunning=false
                }).start()






            }
            else -> Unit



        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->

            takePictureIntent.resolveActivity(packageManager)?.also {

                val photoFile: File? = try {
                    File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "To2decode.jpg")
                } catch (ex: IOException) {
                    // Error occurred while creating the File

                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.shubhamvashishth.sudokusolver",
                        it
                    )
                   takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    var REQUEST_IMAGE_CAPTURE = 0
                   startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)




                }
            }
        }
    }

    lateinit var currentPhotoPath: String


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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
         level= position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }



    override fun onResume() {
        super.onResume()
        a.visibility=View.GONE


    }

    override fun onBackPressed() {
        super.onBackPressed()
finishAffinity()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            startActivity(Intent(this,mlreader::class.java))

        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
        }
        else if(resultCode== -1
        ){
            var uri: Uri= Uri.fromFile(File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),"To2decode.jpg"))
            var options= UCrop.Options()
            var bc=UCrop.of(uri, Uri.fromFile(File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Todecode.jpg"))).withOptions(options).withAspectRatio(1f,1f)
            bc.start(this)
        }
    }



}


