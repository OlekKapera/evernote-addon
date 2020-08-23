package com.aleksanderkapera.evernoteaddon.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.aleksanderkapera.evernoteaddon.App
import com.aleksanderkapera.evernoteaddon.R
import com.aleksanderkapera.evernoteaddon.util.asString
import com.evernote.android.intent.CreateNewNoteIntentBuilder
import com.evernote.android.intent.EvernoteIntent

class MainFragmentViewModel : ViewModel() {

    var convertedSpeech: String? = null

    /**
     * Returns intent to save text as a note
     */
    fun getNoteIntent(text: String): Intent {
        val titleKeyWord = R.string.key_word_title.asString()
        val title = text.replace(Regex(titleKeyWord, RegexOption.IGNORE_CASE), titleKeyWord)
            .substringAfter(R.string.key_word_title.asString())
            .substringBefore(R.string.key_word_title.asString())
        return EvernoteIntent.createNewNote()
            .setTitle(title)
            .setTextPlain(text)
            .setSourceApp(App.context.packageName)
            .setAppVisibility(CreateNewNoteIntentBuilder.AppVisibility.NO_UI)
            .addTags("Evernote Add-on")
            .create()
    }
}