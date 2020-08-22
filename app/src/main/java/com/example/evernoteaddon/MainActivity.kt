package com.example.evernoteaddon

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.evernoteaddon.util.asString
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val requestPermissionCode = 100

    private lateinit var root: View

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        val binding: ActivityMainBinding =
            DataBindingUtil.inflate<ActivityMainBinding>(
                layoutInflater,
                R.layout.activity_main,
                container,
                false
            )
        setContentView(R.layout.activity_main)

        checkPermissions()

        val recognizer = SpeechRecognizer.createSpeechRecognizer(this)

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
        }

        recognizer.setRecognitionListener(object : RecognitionListener {
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

        mainActivity_button.setOnClickListener {
            recognizer.startListening(intent)
        }

        return root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            requestPermissionCode -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("aa", "Permission has been denied by user")
                } else {
                    Snackbar.make(
                        ,
                        R.string.error_permissions_not_granted.asString(),
                        Snackbar.LENGTH_LONG
                    )
                    Log.i("aa", "Permission has been granted by user")
                }
            }
        }
    }

    private fun checkPermissions() {
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                requestPermissionCode
            )
        }
    }
}