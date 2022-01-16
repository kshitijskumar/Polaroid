package com.example.polaroid.core

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.example.polaroid.transformations.PolaroidTransformations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PolaroidCamera {
    private lateinit var polaroid: Polaroid

    private var scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private var imageUrl: String = ""

    @DrawableRes
    @ColorRes
    private var placeholder: Int? = null

    private var onSuccessLoad: (bmp: Bitmap) -> Unit = {}
    private var onFailedLoad: (t: Exception) -> Unit = {}
    private var onAllCallback: (bmp: Bitmap?, e: Exception?) -> Unit = { _, _ -> }

    private var transformImage: PolaroidTransformations = PolaroidTransformations.NoTransformation

    infix fun scopedTo(scope: CoroutineScope) {
        this.scope = scope
    }

    infix fun withPlaceholder(placeholder: Int?) {
        this.placeholder = placeholder
    }

    infix fun transformsInto(transformation: PolaroidTransformations) {
        this.transformImage = transformation
    }

    fun imageUrl(init: PolaroidCamera.() -> String) {
        this.imageUrl = this.init()
    }

    infix fun onSuccessLoad(callback: (bmp: Bitmap) -> Unit) {
        this.onSuccessLoad = callback
    }

    infix fun onFailedLoad(callback: (t: Exception) -> Unit) {
        this.onFailedLoad = callback
    }

    infix fun onGenericCallback(callback: (bmp: Bitmap?, e: Exception?) -> Unit) {
        this.onAllCallback = callback
    }

    fun display(imageView: ImageView?): Int {
        this.polaroid = Polaroid.getPolaroid(
            imageView,
            scope,
            imageUrl,
            placeholder,
            onSuccessLoad,
            onFailedLoad,
            onAllCallback,
            transformImage
        )
        val workerJob = polaroid.scope.launch {
            polaroid.fetchAndLoadImage(this.hashCode())
        }.apply {
            Polaroid.submitJob(this.hashCode(), this)
        }
        return workerJob.hashCode()
    }

}

fun ImageView.loadImage(url: String, options: PolaroidCamera.() -> Unit = {}): Int {
    val polaroidCamera = PolaroidCamera().apply {
        imageUrl { url }
        this.options()
    }
    return polaroidCamera.display(this)
}