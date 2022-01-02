package com.example.polaroid.utils

object PolaroidConfig {

    val maxMemory: Long get() = Runtime.getRuntime().maxMemory() / 1024

    val cacheMemory: Long get() = maxMemory / 8

}