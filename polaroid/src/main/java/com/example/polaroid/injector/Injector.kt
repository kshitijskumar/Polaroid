package com.example.polaroid.injector

import com.example.polaroid.cache.PolaroidCache
import com.example.polaroid.cache.PolaroidMemoryCache
import com.example.polaroid.core.ResourceRepository
import com.example.polaroid.core.ResourceRepositoryImpl
import com.example.polaroid.network.ResourceFetcher
import com.example.polaroid.network.ResourceFetcherImpl
import com.example.polaroid.transformations.ImageTransformerHelper
import com.example.polaroid.transformations.ImageTransformerHelperImpl
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

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
    val dispatchers: DispatcherProvider by lazy {
        object : DispatcherProvider {
            override val ioDispatcher: CoroutineContext
                get() = Dispatchers.IO
            override val mainDispatcher: CoroutineContext
                get() = Dispatchers.Main
        }
    }

}

interface DispatcherProvider {
    val ioDispatcher: CoroutineContext
    val mainDispatcher: CoroutineContext
}