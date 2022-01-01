package com.example.polaroid.transformations

import android.graphics.*
import kotlin.math.min

interface ImageTransformerHelper {
    fun transformImage(sourceBitmap: Bitmap, transformation: PolaroidTransformations) : Bitmap
}

class ImageTransformerHelperImpl : ImageTransformerHelper {

    override fun transformImage(
        sourceBitmap: Bitmap,
        transformation: PolaroidTransformations,
    ): Bitmap {
        return when(transformation) {
            is PolaroidTransformations.NoTransformation -> {
                sourceBitmap
            }
            is PolaroidTransformations.CircularTransformation -> {
                return doCircularTransformation(sourceBitmap)
            }
            is PolaroidTransformations.CustomCornerTransformation -> {
                doCorneredTransformation(
                    sourceBitmap,
                    transformation.tl,
                    transformation.tr,
                )
            }
            is PolaroidTransformations.RoundedCornerTransformation -> {
                doCorneredTransformation(
                    sourceBitmap,
                    transformation.all,
                    transformation.all,
                )
            }
        }
    }

    private fun doCircularTransformation(src: Bitmap) : Bitmap {
        val width = src.width
        val height = src.height

        val radius = min(width, height)/2

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)


        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
        }
        canvas.drawCircle(width.toFloat()/2, height.toFloat()/2, radius.toFloat(), paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        val rect = Rect(0, 0, width, height)
        canvas.drawBitmap(src, rect, rect, paint)

        return output
    }

    private fun doCorneredTransformation(src: Bitmap, tl: Int, tr: Int) : Bitmap {
        val width = src.width
        val height = src.height

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
        }

        val rect = Rect(0, 0, width, height)
        canvas.drawRoundRect(RectF(rect), tl.toFloat(), tr.toFloat(), paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        canvas.drawBitmap(src, rect, rect, paint)
        return output
    }
}