package com.path_studio.githubuser.alarm

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.path_studio.githubuser.R
import com.path_studio.githubuser.activities.SplashActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val TYPE_REPEATING = "Github User"
        const val EXTRA_MESSAGE = "message"
        private const val ID_REPEATING = 101
        private const val TIME_FORMAT = "HH:mm"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        val notifId = ID_REPEATING

        if (message != null) {
            showAlarmNotification(context, message, notifId)
        }
    }

    private fun showAlarmNotification(context: Context, message: String, notifId: Int) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "AlarmManager channel"

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val icon = BitmapFactory.decodeResource(
                context.resources,
            R.drawable.github_icon
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_access_time_black_24)
                .setContentTitle(TYPE_REPEATING)
                .setContentText(context.getText(R.string.daily_reminder))
                .setLargeIcon(icon)
                .setStyle(
                        NotificationCompat.BigTextStyle()
                                .bigText(message)
                )
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)

        val splashIntent = Intent(context, SplashActivity::class.java)
        splashIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP
                or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val intent = PendingIntent.getActivity(
                context, 0,
                splashIntent, 0
        )

        builder.setContentIntent(intent)

        //For android Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(CHANNEL_ID)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        builder.setAutoCancel(true)
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    fun setRepeatingAlarm(context: Context, time: String, message: String) {
        if (isDateInvalid(time)) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)

        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        )

        Toast.makeText(context, context.getText(R.string.success_set_reminder), Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = ID_REPEATING
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(
                context,
                context.getText(R.string.success_remove_reminder),
                Toast.LENGTH_SHORT
        ).show()
    }

    // Check if this alarm already registered in Alarm manager or not
    fun isAlarmSet(context: Context): Boolean {
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = ID_REPEATING

        return PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_NO_CREATE
        ) != null
    }

    // Date dan time validation
    private fun isDateInvalid(date: String): Boolean {
        return try {
            val df = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
            df.isLenient = false
            df.parse(date)
            false
        } catch (e: ParseException) {
            true
        }
    }

}