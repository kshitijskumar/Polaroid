package com.example.polaroid.injector

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

}