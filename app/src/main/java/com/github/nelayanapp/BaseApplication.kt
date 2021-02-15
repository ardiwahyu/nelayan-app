package com.github.nelayanapp

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication: MultiDexApplication() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()
//        Sentry.init("https://bffb2a67d9f44e858b8e31d29e4e93c1@sentry.siap.id/12", AndroidSentryClientFactory(this))
        firebaseAnalytics = Firebase.analytics
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        Stetho.initializeWithDefaults(this)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            setupNotificationChannel()
//        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    fun setupNotificationChannel() {
//        val notificationChannel = NotificationChannel(
//            getString(R.string.default_notification_channel_id),
//            getString(R.string.default_notification_channel_id),
//            NotificationManager.IMPORTANCE_HIGH
//        )
//        notificationChannel.setShowBadge(true)
//        notificationChannel.enableLights(true)
//        notificationChannel.enableVibration(true)
//        notificationChannel.lightColor = Color.GREEN
//        val notificationManager: NotificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(notificationChannel)
//    }
}