package com.aleksanderkapera.evernoteaddon.util

import android.view.View
import com.aleksanderkapera.evernoteaddon.App
import com.google.android.material.snackbar.Snackbar


fun Int.asString(): String = App.context.getString(this)

fun View.snack(text: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, text, length).show()
}