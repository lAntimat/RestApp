package ru.cometrica.arbook.util

import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable

fun List<Drawable>.makeAnimation(frameDuration: Int = 35) =
    AnimationDrawable().also { animation ->
        forEach { drawable ->
            animation.addFrame(drawable, frameDuration)
        }
    }