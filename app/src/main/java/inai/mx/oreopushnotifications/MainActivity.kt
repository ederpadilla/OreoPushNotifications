package inai.mx.oreopushnotifications

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import android.support.v4.app.NotificationCompat
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.provider.Settings
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity() {
    var bitmap : Bitmap?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Glide.with(this)
                .asBitmap().load("https://media.aweita.larepublica.pe/678x508/aweita/imagen/2018/02/08/noticia-los-simpsons-datos-curiosos.jpg")
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap, model: Any, target: Target<Bitmap>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        bitmap = resource
                        return false
                    }
                })
                .submit()
    }
    var number = 0

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification(view : View){
        Log.e("sdf","sdfsdf")
        val mContext: Context = this
        val mNotificationManager: NotificationManager
        val mBuilder: NotificationCompat.Builder
        val resultIntent = Intent(applicationContext, MainActivity::class.java)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val resultPendingIntent = PendingIntent.getActivity(applicationContext,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        val notiStyle = NotificationCompat.BigPictureStyle()
        notiStyle.setSummaryText("HOlaaaa ")
        notiStyle.bigPicture(bitmap)
        mBuilder = NotificationCompat.Builder(mContext,"1")
        mBuilder.setSmallIcon(R.drawable.ic_pizza_slice)
        mBuilder.setContentTitle(title)
                .setContentText("Hola")
                .setAutoCancel(false)
                .setStyle(notiStyle)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent)

        mNotificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel("1", "NOTIFICATION_CHANNEL_NAME", importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            assert(mNotificationManager != null)
            mBuilder.setChannelId("1")
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
        assert(mNotificationManager != null)
        mNotificationManager.notify(number /* Request Code */, mBuilder.build())
        number ++
    }
}
