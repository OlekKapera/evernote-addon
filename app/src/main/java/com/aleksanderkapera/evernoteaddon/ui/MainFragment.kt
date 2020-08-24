package com.aleksanderkapera.evernoteaddon.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aleksanderkapera.evernoteaddon.App
import com.aleksanderkapera.evernoteaddon.R
import com.aleksanderkapera.evernoteaddon.databinding.FragmentMainBinding
import com.aleksanderkapera.evernoteaddon.util.INTENT_ACTION_OPEN_MAIN_FRAGMENT
import com.aleksanderkapera.evernoteaddon.util.REQUEST_PERMISSION_CODE
import com.aleksanderkapera.evernoteaddon.util.asString
import com.aleksanderkapera.evernoteaddon.util.snack
import com.aleksanderkapera.evernoteaddon.viewmodel.MainFragmentViewModel
import com.evernote.android.intent.EvernoteIntent

class MainFragment : Fragment() {

    private val viewModel: MainFragmentViewModel by viewModels()

    private lateinit var binding: FragmentMainBinding
    private lateinit var navController: NavController

    private val recognizer = SpeechRecognizer.createSpeechRecognizer(App.context)
    private val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_main,
                container,
                false
            )
        navController = findNavController()

        when (arguments?.getString("extra")) {
            INTENT_ACTION_OPEN_MAIN_FRAGMENT -> {
            }
            else -> navController.navigate(MainFragmentDirections.navActionMainToSettings())
        }
        checkPermissions()

        recognizer.setRecognitionListener(
            object : RecognitionListener {
                override fun onReadyForSpeech(p0: Bundle?) {}
                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(p0: Float) {}
                override fun onBufferReceived(p0: ByteArray?) {}
                override fun onEndOfSpeech() {}
                override fun onError(p0: Int) {}
                override fun onPartialResults(p0: Bundle?) {}
                override fun onEvent(p0: Int, p1: Bundle?) {}

                override fun onResults(results: Bundle?) {
                    val resultString = results?.getStringArrayList(
                        SpeechRecognizer.RESULTS_RECOGNITION
                    )?.first() ?: ""
                    viewModel.convertedSpeech = resultString

                    binding.root.snack(resultString)
                    if (EvernoteIntent.isEvernoteInstalled(App.context))
                        startActivity(viewModel.getNoteIntent(resultString))
                    else
                        binding.root.snack(R.string.error_evernote_not_installed.asString())
                }
            })

        recognizer.startListening(recognizerIntent)

        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(MainFragment::class.simpleName, "Permission has been denied by user")
                } else {
                    binding.root.snack(R.string.error_permissions_not_granted.asString())
                    Log.i(MainFragment::class.simpleName, "Permission has been granted by user")
                }
            }
        }
    }

    /**
     * Request required permissions from user if they haven't been already granted
     */
    private fun checkPermissions() {
        val permission =
            ContextCompat.checkSelfPermission(App.context, Manifest.permission.RECORD_AUDIO)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_PERMISSION_CODE
            )
        }
    }
}