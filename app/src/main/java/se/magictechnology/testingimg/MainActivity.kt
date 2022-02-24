package se.magictechnology.testingimg

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {

    private var imguri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val img = registerForActivityResult(ActivityResultContracts.GetContent(), ActivityResultCallback {
            findViewById<ImageView>(R.id.theimagev).setImageURI(it)
        })
        val imgCamera = registerForActivityResult(ActivityResultContracts.TakePicture(), ActivityResultCallback {
            findViewById<ImageView>(R.id.theimagev).setImageURI(imguri!!)
        })


        findViewById<Button>(R.id.camerabutton).setOnClickListener {
            val tmpFile = File.createTempFile("tmp_image_file", ".png", cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }

            imguri = FileProvider.getUriForFile(applicationContext, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)


            //imgCamera.launch(photoURI)
            imgCamera.launch(imguri)
        }

        findViewById<Button>(R.id.gallerybutton).setOnClickListener {
            img.launch("image/*")
        }
    }
}