package com.example.polaroid.core

import android.util.Log
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.example.polaroid.injector.Injector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class Polaroid private constructor() {

    private val TAG = "Polaroid"

    lateinit var scope: CoroutineScope
    var imageUrlToFetchFrom: String = ""
    var imageViewToLoadInto: ImageView? = null

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
        val bitmap = Injector.resourceFetcher.fetchResource(url = imageUrlToFetchFrom)
        imageViewToLoadInto?.setImageBitmap(bitmap)
        findAndCancelCurrentJob(jobId)
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