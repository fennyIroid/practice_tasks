package com.example.practice_tasks.task16PushNotifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.practice_tasks.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data

        // ✅ Keys MUST match Firebase custom data
        val title = message.notification?.title ?: data["title"] ?: "Study Reminder"
        val body = message.notification?.body ?: data["message"] ?: "You have a study task"
        val reminderId = data["reminderId"] ?: "0"

        showReminderNotification(title, body, reminderId)
    }

    private fun showReminderNotification(
        title: String,
        body: String,
        reminderId: String
    ) {
        val channelId = "study_reminder_channel" // ✅ MUST match Firebase

        // ✅ Create notification channel (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Study Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for study reminders"
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        // ✅ Intent on notification tap
        val intent = Intent(this, ReminderDetailsActivity::class.java).apply {
            putExtra("reminderId", reminderId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            reminderId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // ✅ Build notification
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("📘 $title")
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        // ✅ Show notification
        NotificationManagerCompat.from(this).notify(
            reminderId.toInt(),
            notification
        )
    }
}
