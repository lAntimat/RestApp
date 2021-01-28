package ru.cometrica.arbook.util

import android.graphics.drawable.AnimationDrawable
import android.widget.ImageView


fun ImageView.startAnimation(animation: AnimationDrawable) {
    animation.isOneShot = false
    animation.setVisible(true, true)
    setImageDrawable(animation)
    animation.start()
}
