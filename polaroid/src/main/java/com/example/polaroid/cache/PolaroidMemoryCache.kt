package com.example.polaroid.cache

import android.graphics.Bitmap
import android.util.LruCache
import com.example.polaroid.utils.PolaroidConfig

class PolaroidMemoryCache : PolaroidCache {

    private var memoryCache: LruCache<String, Bitmap> = object : LruCache<String, Bitmap>(PolaroidConfig.cacheMemory.toInt()) {
        override fun sizeOf(key: String?, value: Bitmap?): Int {
            return value?.byteCount?.div(1024) ?: 1024
        }
    }

    override fun addBitmapToCache(key: String, bitmap: Bitmap) {
        memoryCache.put(key, bitmap)
    }

    override fun getBitmapForKey(key: String): Bitmap? {
        return memoryCache.get(key)
    }

    override fun removeBitmapForKey(key: String) {
        memoryCache.remove(key)
    }

    override fun clearCache() {
        memoryCache.evictAll()
    }
}