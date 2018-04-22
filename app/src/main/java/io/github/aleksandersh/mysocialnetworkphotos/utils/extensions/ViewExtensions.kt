package io.github.aleksandersh.mysocialnetworkphotos.utils.extensions

import android.view.View
import android.widget.TextView

/**
 * Fast changes of View visibility by [View.VISIBLE] and [View.INVISIBLE].
 */
var View.isShowed: Boolean
    get() = this.visibility == View.VISIBLE
    set(value) {
        this.visibility = if (value) View.VISIBLE else View.INVISIBLE
    }

/**
 * Fast changes of View visibility by [View.VISIBLE] and [View.GONE].
 */
var View.isDisplayed: Boolean
    get() = this.visibility == View.VISIBLE
    set(value) {
        this.visibility = if (value) View.VISIBLE else View.GONE
    }

fun TextView.setTextOrHide(text: String?) {
    if (text != null) {
        this.text = text
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}