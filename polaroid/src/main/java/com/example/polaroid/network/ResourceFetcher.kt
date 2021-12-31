package com.example.polaroid.network

import android.graphics.Bitmap

interface ResourceFetcher {

    suspend fun fetchResource(url: String) : Bitmap?
}