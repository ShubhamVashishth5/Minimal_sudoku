package com.shubhamvashishth.sudokusolver

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizerOptions
import java.io.IOException
import java.net.URI
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Environment
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.shubhamvashishth.sudokusolver.boardview.useClass
import java.nio.ByteBuffer


class mlreader : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mlreader)




       //var bitmapImage = BitmapFactory.decodeFile("/sdcard/Android/data/com.shubhamvashishth.sudokusolver/files/Pictures/todecode.jpg")
        var path= getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()+"/todecode.jpg"
        var bitmapImage= BitmapFactory.decodeFile(path)

        var bitarray= mutableListOf<Bitmap>()

        var width= bitmapImage.width/9
        var height= bitmapImage.height/9

        bitmapImage= Bitmap.createScaledBitmap(bitmapImage, width*9,width*9,true)
        height=width


        for( i in 0..8)
            for(j in 0..8)
                bitarray.add( Bitmap.createBitmap(bitmapImage, i*width, j*height, width, height))



        for( i in 0..8)
            for(j in 0..8) {
                var st8= recognizeText(InputImage.fromBitmap(bitarray[i*9+j],0),i,j)

            }




    }

    private fun recognizeText(image: InputImage, i:Int, j:Int) : String{


        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        var res=""
        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->

                res=visionText.text


                var a: Int?
                if(res=="")a=null
                else a= res[0].code- '0'.code
                if(a!=null)if(a<0||a>9)a=null
                useClass.sugrid[j * 9 + i].value = a
                useClass.sugrid[j * 9 + i].isStartingCell=false

                if(i==8 && j==8){
                    useClass.equal(useClass.ugrid,useClass.sugrid)
                    useClass.equal(useClass.qgrid,useClass.sugrid)


                    var intent= Intent(this,verifyorfill::class.java)
                    startActivity(intent)
                }

            }
            .addOnFailureListener { e ->

            }
        return res
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
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or   View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }




}