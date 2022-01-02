package com.example.polaroid.cache

import android.graphics.Bitmap

interface PolaroidCache {

    fun addBitmapToCache(key: String, bitmap: Bitmap)
    fun getBitmapForKey(key: String) : Bitmap?
    fun removeBitmapForKey(key: String)
    fun clearCache()

}