package com.example.polaroid.core

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.example.polaroid.injector.Injector
import com.example.polaroid.utils.ResultHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlin.Exception

class Polaroid private constructor() {

    private val TAG = "Polaroid"

    lateinit var scope: CoroutineScope
    var imageUrlToFetchFrom: String = ""
    var imageViewToLoadInto: ImageView? = null

    var imageLoadErrorCallback: (t: Exception) -> Unit = {}
    var imageLoadSuccessCallback: (bmp: Bitmap) -> Unit = {}
    var genericLoadCallback: (bmp: Bitmap?, e: Exception?) -> Unit = {bmp, e -> }

    @DrawableRes
    @ColorRes
    var placeholderWhileFetching: Int? = null

    private constructor(scope: CoroutineScope) : this() {
        this.scope = scope
    }

    suspend fun fetchAndLoadImage(jobId: Int) {
        if (!shouldTryFetchingAndLoading()) {
            findAndCancelCurrentJob(jobId)
            return
        }
        handlePreFetchPlaceholder()
        handleFetchAndSet()
        findAndCancelCurrentJob(jobId)
    }

    private suspend fun handleFetchAndSet() {
        when(val bitmapResult = Injector.resourceFetcher.fetchResource(url = imageUrlToFetchFrom)) {
            is ResultHolder.Success -> {
                sendSuccessCallback(bitmapResult.result)
                imageViewToLoadInto?.setImageBitmap(bitmapResult.result)
            }
            is ResultHolder.Error -> {
                sendErrorCallback(bitmapResult.e)
            }
        }
    }

    private fun sendSuccessCallback(bmp: Bitmap) {
        imageLoadSuccessCallback.invoke(bmp)
        genericLoadCallback.invoke(bmp, null)
    }

    private fun sendErrorCallback(e: Exception) {
        imageLoadErrorCallback.invoke(e)
        genericLoadCallback.invoke(null, e)
    }

    private fun handlePreFetchPlaceholder() {
        placeholderWhileFetching?.let {
            imageViewToLoadInto?.setImageResource(it)
        }
    }

    private fun findAndCancelCurrentJob(jobId: Int) {
        val currentJob = imageFetchingJobs.remove(jobId)
        currentJob?.cancel()
    }

    private fun shouldTryFetchingAndLoading(): Boolean {
        return when {
            imageUrlToFetchFrom.isEmpty() -> {
                Log.e(TAG, "please provide a valid url")
                false
            }
            imageViewToLoadInto == null -> {
                Log.e(TAG, "imageview to load into not provided")
                false
            }
            else -> {
                true
            }
        }
    }

    companion object {

        private val imageFetchingJobs = mutableMapOf<Int, Job>()

        fun submitJob(jobId: Int, job: Job) {
            imageFetchingJobs[jobId] = job
        }

        fun getPolaroid(scope: CoroutineScope) : Polaroid {
            return Polaroid(scope)
        }

        fun cancelImageLoading(jobId: Int) {
            imageFetchingJobs.remove(jobId)?.apply {
                cancel()
            }
        }

    }

}