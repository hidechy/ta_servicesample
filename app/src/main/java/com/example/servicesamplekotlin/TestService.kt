package com.example.servicesamplekotlin

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi

class TestService : Service() {

    private lateinit var timer: CountDownTimer

    var cnt = 0

    val timeInstance = Time.getInstance()

    override fun onCreate() {
        super.onCreate()
        Log.d("debug", "onCreate()")

        //1)開始時間を設定
        val startTime: Long = 10000 //10秒

        cnt = 0

        //4)カウントダウンタイマーのオブジェクトを用意
        timer = object : CountDownTimer(startTime, 100) {
            //[4-1]途中経過・残り時間
            override fun onTick(p0: Long) {
                println("count down $cnt")
                cnt++

                timeInstance.time = cnt


            }

            //[4-2]終了設定
            override fun onFinish() {
                println("count finish")
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("debug", "onStartCommand()")

        val requestCode = intent.getIntExtra("REQUEST_CODE", 0)
        val context: Context = getApplicationContext()
        val channelId = "default"
        val title: String = context.getString(R.string.app_name)

        val pendingIntent = PendingIntent.getActivity(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Notification　Channel 設定
        val channel = NotificationChannel(
            channelId, title, NotificationManager.IMPORTANCE_DEFAULT
        )

        notificationManager.createNotificationChannel(channel)

        val notification: Notification = Notification.Builder(context, channelId)
            .setContentTitle(title) // android標準アイコンから
            .setContentText("MediaPlay")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setWhen(System.currentTimeMillis())
            .build()

        // startForeground
        startForeground(1, notification)
        println("countup!!")
        countTime()
        return START_NOT_STICKY
        //return START_STICKY;
        //return START_REDELIVER_INTENT;
    }

    fun countTime() {
        timer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("debug", "onDestroy()")

        // Service終了
        stopSelf()
    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
