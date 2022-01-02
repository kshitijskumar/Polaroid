package com.example.imageloader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.example.polaroid.core.Polaroid
import com.example.polaroid.core.PolaroidCamera
import com.example.polaroid.transformations.PolaroidTransformations

class MainActivity : AppCompatActivity() {

    private val imageUrl: String = "https://picsum.photos/200"


    private val imageView: ImageView by lazy {
        findViewById(R.id.image_view)
    }

    private val imageView2: ImageView by lazy {
        findViewById(R.id.image_view2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        PolaroidCamera()
            .scoped(lifecycleScope)
            .load("https://static0.srcdn.com/wordpress/wp-content/uploads/2019/10/Shikamaru-Nara-In-Naruto.jpg?q=50&fit=crop&w=960&h=500&dpr=1.5")
            .into(imageView)
            .with(R.color.black)
            .onSuccessLoad {
                Log.d("ImageLoad", "loaded: $it")
            }
            .onFailedLoad {
                Log.d("ImageLoad", "error: $it")
            }
            .display()

        val jobId = PolaroidCamera()
            .scoped(lifecycleScope)
            .load("https://www.ubuy.co.in/productimg/?image=aHR0cHM6Ly9tLm1lZGlhLWFtYXpvbi5jb20vaW1hZ2VzL0kvNzFCUERGNlYxRkwuX0FDX1NMMTUwMF8uanBn.jpg")
            .into(imageView2)
            .with(R.drawable.ic_launcher_foreground)
            .onGenericCallback { bmp, e ->
                Log.d("ImageLoad", "generic: $bmp or $e")
            }
            .transformImage(PolaroidTransformations.RoundedCornerTransformation(20))
            .display()
//
//        lifecycleScope.launch {
//            delay(3000)
//            Polaroid.cancelImageLoading(jobId)
//        }

    }
}