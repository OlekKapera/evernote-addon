package com.aleksanderkapera.evernoteaddon.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.evernote.android.intent.EvernoteIntent

class MainFragmentViewModel : ViewModel() {

    var convertedSpeech: String? = null

    /**
     * Returns intent to save text as a note
     */
    fun getNoteIntent(text: String): Intent {
        return EvernoteIntent.createNewNote()
            .setTextPlain(text)
            .create()
    }
}