package pl.kuzbin.tomnote

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


fun createNotification(activity: Activity, title: String, text: String) {
    val channelId = "TomNote_notification_channel"
    val channelName = "TomNote Notification Channel"
    val notificationId = 1 // TODO may need to increment for each next notification

    // Create a NotificationChannel for Android Oreo and above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)
        val notificationManager: NotificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // Build the notification
    val builder = NotificationCompat.Builder(activity, channelId)
        //.setSmallIcon(R.drawable.notification_icon) // TODO add icon
        .setContentTitle(title)
        .setContentText(text)
        .setSmallIcon(R.drawable.sticky_note_2_24px)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    // Show the notification
    with(NotificationManagerCompat.from(activity)) {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            } else {
                val toast = Toast(activity)
                toast.setText("Add notification permission")
                toast.duration = Toast.LENGTH_LONG
                toast.show()
            }
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notify(notificationId, builder.build())
    }
}