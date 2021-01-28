package ru.cometrica.arbook.util

import android.content.Context
import android.content.pm.PackageManager

object CameraUtil {
    fun hasCamera(context: Context) =
        context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
}