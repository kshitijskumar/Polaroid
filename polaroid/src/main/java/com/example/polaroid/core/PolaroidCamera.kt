package com.example.polaroid.core

import android.util.Log
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import kotlinx.coroutines.*

class PolaroidCamera {
    private lateinit var polaroid: Polaroid

    fun scoped(scope: CoroutineScope) : PolaroidCamera {
        return apply {
            this.polaroid = Polaroid.getPolaroid(scope)
        }
    }

    fun scoped() : PolaroidCamera {
        val scope = CoroutineScope(Dispatchers.Main)
        return apply {
            this.polaroid = Polaroid.getPolaroid(scope)
        }
    }

    fun load(imageUrl: String) : PolaroidCamera {
        return apply {
            this.polaroid.imageUrlToFetchFrom = imageUrl
        }
    }

    fun with(@DrawableRes @ColorRes placeholder: Int) : PolaroidCamera {
        return apply {
            this.polaroid.placeholderWhileFetching = placeholder
        }
    }

    fun into(imageView: ImageView?) : PolaroidCamera {
        return apply {
            polaroid.imageViewToLoadInto = imageView
        }
    }

    fun display() : Int {
        val workerJob = polaroid.scope.launch {
            polaroid.fetchAndLoadImage(this.hashCode())
        }.apply {
            Polaroid.submitJob(this.hashCode(), this)
        }
        return workerJob.hashCode()
    }

}