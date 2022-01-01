package com.example.polaroid.network

import android.graphics.Bitmap
import com.example.polaroid.utils.ResultHolder

interface ResourceFetcher {

    suspend fun fetchResource(url: String) : ResultHolder<Bitmap>
}