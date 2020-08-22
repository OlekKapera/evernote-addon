package com.example.evernoteaddon.util

import com.example.evernoteaddon.App


fun Int.asString(): String = App.context.getString(this)