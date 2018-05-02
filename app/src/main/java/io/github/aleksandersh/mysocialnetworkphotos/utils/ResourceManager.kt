package io.github.aleksandersh.mysocialnetworkphotos.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

/**
 * Created by aleksandersh on 3/31/18.
 */
class ResourceManager(private val context: Context) {

    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    fun getBitmap(@DrawableRes resId: Int): Bitmap {
        return BitmapFactory.decodeResource(context.resources, resId)
    }
}