package com.example.polaroid.core

import android.content.Context
import android.graphics.Bitmap
import com.example.polaroid.utils.ResultHolder

interface ResourceRepository {

    suspend fun getBitmapForUrl(context: Context, imageUrl: String) : ResultHolder<Bitmap>

    suspend fun addFetchedResultInCache(imageUrl: String, result: ResultHolder<Bitmap>) : ResultHolder<Bitmap>
}