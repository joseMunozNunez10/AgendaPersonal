package com.jmdevs.agendapersonal.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.jmdevs.agendapersonal.data.local.entity.Event
import com.jmdevs.agendapersonal.receiver.NotificationReceiver
import java.text.SimpleDateFormat
import java.util.Locale

class AlarmScheduler(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun schedule(event: Event) {


         val triggerAtMillis = event.dateMillis - 86400000 // 24 horas antes
         if (triggerAtMillis < System.currentTimeMillis()) return

        val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }

        if (hasPermission) {
            val intent = Intent(context, NotificationReceiver::class.java).apply {
                putExtra(NotificationReceiver.EXTRA_EVENT_TITLE, event.title)
                putExtra(NotificationReceiver.EXTRA_EVENT_TIME, formatTime(event.dateMillis))
                putExtra(NotificationReceiver.EXTRA_EVENT_ID_STRING, event.id)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                event.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        }
    }

    fun cancel(event: Event) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            event.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    private fun formatTime(millis: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(millis)
    }
}