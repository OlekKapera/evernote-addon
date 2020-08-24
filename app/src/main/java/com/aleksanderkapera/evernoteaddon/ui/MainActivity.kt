package com.aleksanderkapera.evernoteaddon.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.aleksanderkapera.evernoteaddon.R
import com.aleksanderkapera.evernoteaddon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        navController = findNavController(R.id.fragment_navigation)
        navController.setGraph(R.navigation.nav, intent.extras)
    }
}