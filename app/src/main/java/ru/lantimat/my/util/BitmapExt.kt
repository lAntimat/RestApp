package ru.cometrica.arbook.util

import android.R.attr.radius
import android.graphics.*


fun Bitmap.roundCorners() {
    val bitmapShader = BitmapShader(this, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    val paint = Paint().apply {
        isAntiAlias = true
        shader = bitmapShader
    }
    val rect = RectF(0.0f, 0.0f, width.toFloat(), height.toFloat())
    Canvas().drawRoundRect(rect, radius.toFloat(), radius.toFloat(), paint)
}