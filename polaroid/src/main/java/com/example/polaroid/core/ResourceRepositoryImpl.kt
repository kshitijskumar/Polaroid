package com.example.polaroid.core

import android.content.Context
import android.graphics.Bitmap
import com.example.polaroid.cache.PolaroidCache
import com.example.polaroid.injector.Injector
import com.example.polaroid.network.ResourceFetcher
import com.example.polaroid.utils.ResultHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResourceRepositoryImpl(
    private val resourceFetcher: ResourceFetcher = Injector.resourceFetcher,
    private val memoryCache: PolaroidCache = Injector.memoryCache
) : ResourceRepository {

    override suspend fun getBitmapForUrl(context: Context, imageUrl: String): ResultHolder<Bitmap> {
        return withContext(Dispatchers.IO) {
            memoryCache.getBitmapForKey(imageUrl)?.let {
                ResultHolder.Success(it)
            } ?: addFetchedResultInCache(
                imageUrl,
                resourceFetcher.fetchResource(imageUrl)
            )
        }
    }

    override suspend fun addFetchedResultInCache(
        imageUrl: String,
        result: ResultHolder<Bitmap>
    ): ResultHolder<Bitmap> {
        return when(result) {
            is ResultHolder.Success -> {
                memoryCache.addBitmapToCache(imageUrl, result.result)
                ResultHolder.Success(result.result)
            }
            else -> result
        }
    }
}