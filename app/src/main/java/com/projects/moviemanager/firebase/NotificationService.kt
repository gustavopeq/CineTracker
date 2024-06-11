package com.projects.moviemanager.firebase

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.projects.moviemanager.R

class NotificationService : FirebaseMessagingService() {

    @SuppressLint("MissingPermission", "Main activity checking for permissions.")
    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.notification?.title
        val body = message.notification?.body
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat
            .Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSound(defaultSoundUri)
            .setSmallIcon(R.drawable.ic_now_playing)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Push Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }

        val notificationId = 0

        NotificationManagerCompat.from(this).notify(
            notificationId,
            notificationBuilder.build()
        )

        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}
