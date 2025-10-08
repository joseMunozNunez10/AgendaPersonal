package com.jmdevs.agendapersonal.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.jmdevs.agendapersonal.R
import com.jmdevs.agendapersonal.MainActivity

class NotificationReceiver : BroadcastReceiver() {

    companion object {
        const val CHANNEL_ID = "event_notification_channel"
        const val EXTRA_EVENT_TITLE = "extra_event_title"
        const val EXTRA_EVENT_TIME = "extra_event_time"
        const val EXTRA_EVENT_ID_STRING = "extra_event_id_string"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(notificationManager)

        val eventTitle = intent.getStringExtra(EXTRA_EVENT_TITLE) ?: "Evento Próximo"
        val eventTime = intent.getStringExtra(EXTRA_EVENT_TIME) ?: ""
        val eventId = intent.getStringExtra(EXTRA_EVENT_ID_STRING) ?: ""

        val contentIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(eventTitle)
            .setContentText("Mañana a las $eventTime")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(eventId.hashCode(), notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notificaciones de Eventos"
            val descriptionText = "Canal para notificar sobre eventos próximos"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}