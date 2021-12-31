package com.example.polaroid.injector

import com.example.polaroid.network.ResourceFetcher
import com.example.polaroid.network.ResourceFetcherImpl

object Injector {

    val resourceFetcher: ResourceFetcher by lazy {
        ResourceFetcherImpl()
    }

}