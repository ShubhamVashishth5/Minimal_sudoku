package com.shubhamvashishth.sudokusolver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView
import android.view.View
import android.widget.TextView
import com.shubhamvashishth.sudokusolver.boardview.useClass
import java.io.File

class goodjob : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goodjob)
        var txt =findViewById<TextView>(R.id.textView)
        txt.setText("Solved in " + useClass.time+ " s")



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

    override fun onBackPressed() {
        super.onBackPressed()
        var f= File("/data/data/" + packageName + "/shared_prefs/" + "savedata.txt" + ".xml");
        f.delete()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}