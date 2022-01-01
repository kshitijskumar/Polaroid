package com.example.polaroid.transformations

sealed class PolaroidTransformations {
    object NoTransformation : PolaroidTransformations()
    object CircularTransformation : PolaroidTransformations()
    data class RoundedCornerTransformation(val all: Int) : PolaroidTransformations()
    data class CustomCornerTransformation(val tl: Int, val tr: Int) : PolaroidTransformations()
}
