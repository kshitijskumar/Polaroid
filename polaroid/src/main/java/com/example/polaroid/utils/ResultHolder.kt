package com.example.polaroid.utils

import android.graphics.Bitmap

sealed class ResultHolder<out T> {
    data class Success<T>(val result: T) : ResultHolder<T>()
    data class Error(val e: Exception) : ResultHolder<Nothing>()
}
