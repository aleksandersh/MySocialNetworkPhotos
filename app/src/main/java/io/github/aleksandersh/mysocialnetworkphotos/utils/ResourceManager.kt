package io.github.aleksandersh.mysocialnetworkphotos.utils

import android.content.Context
import android.support.annotation.StringRes

/**
 * Created by aleksandersh on 3/31/18.
 */
class ResourceManager(private val context: Context) {

    fun getString(@StringRes res: Int): String {
        return context.getString(res)
    }
}