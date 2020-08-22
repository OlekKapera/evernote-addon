package com.aleksanderkapera.evernoteaddon.util

import com.aleksanderkapera.evernoteaddon.App


fun Int.asString(): String = App.context.getString(this)