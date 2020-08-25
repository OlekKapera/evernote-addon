package com.aleksanderkapera.evernoteaddon.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.aleksanderkapera.evernoteaddon.R
import com.aleksanderkapera.evernoteaddon.util.INTENT_ACTION_OPEN_MAIN_FRAGMENT

//import com.aleksanderkapera.evernoteaddon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        if (intent.action == INTENT_ACTION_OPEN_MAIN_FRAGMENT)
            setTheme(R.style.Theme_Transparent)
        else
            setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.fragment_navigation)
        navController.setGraph(R.navigation.nav, intent.extras)
    }
}