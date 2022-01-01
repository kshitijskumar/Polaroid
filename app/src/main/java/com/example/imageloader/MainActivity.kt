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
            .load(imageUrl)
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
            .load(imageUrl)
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