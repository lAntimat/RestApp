package ru.cometrica.arbook.util

import android.content.res.AssetManager
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

fun AssetManager.readDrawables(drawableFolder: String): List<Drawable> =
    mutableListOf<Drawable>().also { drawables ->
        list(drawableFolder)?.forEach { filename ->
            open("$drawableFolder/$filename").use { imageStream ->
                val drawable = BitmapDrawable.createFromStream(imageStream, "asset")
                drawables.add(drawable)
            }
        }
    }

fun AssetManager.readAnimation(animationFolder: String, frameDuration: Int) =
    readDrawables(animationFolder)
        .makeAnimation(frameDuration)

