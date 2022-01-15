package com.example.imageloader

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.polaroid.core.loadImage
import com.example.polaroid.transformations.PolaroidTransformations

class MainActivity : AppCompatActivity() {

    private val url1 =
        "https://static0.srcdn.com/wordpress/wp-content/uploads/2019/10/Shikamaru-Nara-In-Naruto.jpg?q=50&fit=crop&w=960&h=500&dpr=1.5"
    private val url2 =
        "https://www.ubuy.co.in/productimg/?image=aHR0cHM6Ly9tLm1lZGlhLWFtYXpvbi5jb20vaW1hZ2VzL0kvNzFCUERGNlYxRkwuX0FDX1NMMTUwMF8uanBn.jpg"


    private val imageView: ImageView by lazy {
        findViewById(R.id.image_view)
    }

    private val imageView2: ImageView by lazy {
        findViewById(R.id.image_view2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView.loadImage(url1) {
            transformInto { PolaroidTransformations.CircularTransformation }
            placeholder { R.drawable.ic_launcher_background }
        }

        imageView2.loadImage(url2) {
            scoped { lifecycleScope }
            placeholder { R.color.black }
            onSuccessLoad {
                Log.d("PolaroidLoad", "success")
            }
        }

    }
}