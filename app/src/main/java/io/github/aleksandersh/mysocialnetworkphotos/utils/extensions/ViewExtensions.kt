package io.github.aleksandersh.mysocialnetworkphotos.utils.extensions

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

/**
 * Fast changes of View visibility by [View.VISIBLE] and [View.INVISIBLE].
 */
var View.isInvisible: Boolean
    get() = this.visibility == View.INVISIBLE
    set(value) {
        this.visibility = if (value) View.INVISIBLE else View.VISIBLE
    }

/**
 * Fast changes of View visibility by [View.VISIBLE] and [View.GONE].
 */
var View.isGone: Boolean
    get() = this.visibility == View.GONE
    set(value) {
        this.visibility = if (value) View.GONE else View.VISIBLE
    }

fun TextView.setTextOrHide(text: String?) {
    if (text != null) {
        this.text = text
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun Fragment.requireAppCompatActivity(): AppCompatActivity {
    return this.requireActivity() as AppCompatActivity
}