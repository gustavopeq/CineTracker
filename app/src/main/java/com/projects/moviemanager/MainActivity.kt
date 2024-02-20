package com.projects.moviemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.projects.moviemanager.core.LanguageManager.setUserLocale
import com.projects.moviemanager.firebase.NotificationManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locale = Locale.getDefault()
        setUserLocale(locale)

        notificationManager = NotificationManager(this)
        notificationManager.askNotificationPermission()

        setContent {
            MainApp()
        }
    }
}
