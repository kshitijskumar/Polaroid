package com.example.imageloader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.example.polaroid.core.PolaroidCamera

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
            .load(imageUrl)
            .into(imageView)
            .display()

        val jobId = PolaroidCamera()
            .scoped(lifecycleScope)
            .load(imageUrl)
            .into(imageView2)
            .display()
//
//        lifecycleScope.launch {
//            delay(3000)
//            Polaroid.cancelImageLoading(jobId)
//        }

    }
}