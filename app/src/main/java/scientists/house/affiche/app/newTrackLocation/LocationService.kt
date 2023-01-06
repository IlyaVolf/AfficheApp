package scientists.house.affiche.app.newTrackLocation

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import scientists.house.affiche.app.R
import scientists.house.affiche.app.screens.main.MainActivity

class LocationService : Service() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )

        // new
        createNotificationChannel()

        val upcoming = getLatestEvent()

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        notification = NotificationCompat.Builder(this, channelId)
            .setContentText(
                "${upcoming.second} состоится мероприятие \"${upcoming.first}\".\n" +
                        "Ждём Вас!"
            )
            .setSmallIcon(R.drawable.ic_stat_name)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        "${upcoming.second} состоится мероприятие\"${upcoming.first}\".\n" +
                                "Ждём Вас!"
                    )
            )
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager = NotificationManagerCompat.from(this)

        //val notification = createForegroundNotification()
        //startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notificationDebug = NotificationCompat.Builder(this, "location")
            .setContentTitle("Tracking location...")
            .setContentText("Location: null")
            .setSmallIcon(R.drawable.ic_stat_name)
            .setOngoing(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        Log.d("Debug", "АЛЁ")

        locationClient
            .getLocationUpdates(15000L)
            .catch { e ->
                e.printStackTrace()
                Log.d("Debug", "F5")
            }
            .onEach { location ->
                Log.d("Debug", "МАМА")
                val lat = location.latitude.toString()
                val long = location.longitude.toString()

                // new
                currentLatitude = location.latitude
                currentLongitude = location.longitude
                checkCircleArea()


                val updatedNotification = notificationDebug.setContentText(
                    "Location: ($lat, $long)"
                )
                val message = "Location: ($lat, $long)"
                notificationManager.notify(1, updatedNotification.build())
                Log.d("Debug", message)
            }
            .launchIn(serviceScope)

        startForeground(1, notificationDebug.build())
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"

        const val DUSORAN_LATITUDE = 54.836294
        const val DUSORAN_LONGITUDE = 83.102099

        const val NSU_LATITUDE = 54.843326
        const val NSU_LONGITUDE = 83.089725

        const val HOME_LATITUDE = 55.080041
        const val HOME_LONGITUDE = 82.970251

        // 0.001 = 111 m
        const val LATITUDE_SPREAD = 0.003
        const val LONGITUDE_SPREAD = 0.003
    }

    //////////////////////////////////////

    private val channelId = "channelID"
    private val channelName = "channelName"

    private lateinit var notification: Notification
    private lateinit var notificationManager: NotificationManagerCompat

    private var currentLatitude: Double = 0.0
    private var currentLongitude: Double = 0.0

    private var locationLatitude = DUSORAN_LATITUDE
    private var locationLongitude = DUSORAN_LONGITUDE

    private var isInArea: Boolean = false

    private fun checkCircleArea() {
        isInArea = if ((currentLatitude <= locationLatitude + LATITUDE_SPREAD) &&
            (currentLatitude >= locationLatitude - LATITUDE_SPREAD) &&
            (currentLongitude <= locationLongitude + LONGITUDE_SPREAD) &&
            (currentLongitude >= locationLongitude - LONGITUDE_SPREAD)
        ) {
            Log.d("Debug", "sendNotification")
            if (!isInArea) {
                sendNotification()
            }
            true
        } else {
            false
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        notificationManager.notify(0, notification)
    }

    private fun getLatestEvent(): Pair<String, String> {
        return Pair("Вечер в ресторане", "13 Января")
    }

}