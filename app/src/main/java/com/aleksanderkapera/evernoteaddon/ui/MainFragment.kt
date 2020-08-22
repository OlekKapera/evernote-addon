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
import com.aleksanderkapera.evernoteaddon.App
import com.aleksanderkapera.evernoteaddon.R
import com.aleksanderkapera.evernoteaddon.databinding.FragmentMainBinding
import com.aleksanderkapera.evernoteaddon.util.REQUEST_PERMISSION_CODE
import com.aleksanderkapera.evernoteaddon.util.asString
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

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

        checkPermissions()

        val recognizer = SpeechRecognizer.createSpeechRecognizer(App.context)

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
        }

        recognizer.setRecognitionListener(
            object : RecognitionListener {
                override fun onReadyForSpeech(p0: Bundle?) {
                    Log.d("aa", "red")
                }

                override fun onBeginningOfSpeech() {
                    Log.d("aa", "beg")
                }

                override fun onRmsChanged(p0: Float) {
                }

                override fun onBufferReceived(p0: ByteArray?) {
                }

                override fun onEndOfSpeech() {
                    Log.d("aa", "end")
                }

                override fun onError(p0: Int) {
                }

                override fun onResults(results: Bundle?) {
                    val result = results?.getStringArrayList(
                        SpeechRecognizer.RESULTS_RECOGNITION
                    )
                    Log.d("aa", "results: $result")
                }

                override fun onPartialResults(p0: Bundle?) {
                    Log.d("aa", "par")
                }

                override fun onEvent(p0: Int, p1: Bundle?) {
                }
            })

        mainFragment_button.setOnClickListener {
            recognizer.startListening(intent)
        }

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
                    Log.i("aa", "Permission has been denied by user")
                } else {
                    Snackbar.make(
                        binding.root,
                        R.string.error_permissions_not_granted.asString(),
                        Snackbar.LENGTH_LONG
                    )
                    Log.i("aa", "Permission has been granted by user")
                }
            }
        }
    }

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