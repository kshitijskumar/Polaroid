package com.example.polaroid.injector

import com.example.polaroid.cache.PolaroidCache
import com.example.polaroid.cache.PolaroidMemoryCache
import com.example.polaroid.core.ResourceRepository
import com.example.polaroid.core.ResourceRepositoryImpl
import com.example.polaroid.network.ResourceFetcher
import com.example.polaroid.network.ResourceFetcherImpl
import com.example.polaroid.transformations.ImageTransformerHelper
import com.example.polaroid.transformations.ImageTransformerHelperImpl

object Injector {

    val resourceFetcher: ResourceFetcher by lazy {
        ResourceFetcherImpl()
    }

    val imageTransformerHelper: ImageTransformerHelper
        get() = ImageTransformerHelperImpl()

    val resourceRepository: ResourceRepository by lazy {
        ResourceRepositoryImpl()
    }

    val memoryCache: PolaroidCache by lazy {
        PolaroidMemoryCache()
    }

}