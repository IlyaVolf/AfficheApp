package scientists.house.affiche.app.screens.base

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import scientists.house.affiche.app.R


class NotificationService : Service() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val channelId = "channelID"
    private val channelName = "channelName"

    private lateinit var notification: Notification
    private lateinit var notificationManager: NotificationManagerCompat

    private var currentLatitude: Double = 0.0
    private var currentLongitude: Double = 0.0

    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Debug", "onStartCommand")
        super.onStartCommand(intent, flags, startId)

        startTimer()

        return START_STICKY
    }

    override fun onCreate() {
        Log.d("Debug", "onCreate")

        createNotificationChannel()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Дом ученых")
            .setContentText("Посмотрите наш последний офигенный спектакль")
            .setSmallIcon(R.drawable.ic_affiche)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager = NotificationManagerCompat.from(this)
    }

    override fun onDestroy() {
        Log.d("Debug", "onDestroy")
        stopTimer()
        super.onDestroy()
    }

    //we are going to use a handler to be able to run in our TimerTask
    private val handler: Handler = Handler(Looper.getMainLooper())

    private val runnable = Runnable {
        getLastLocation()
        startTimer()
    }

    private fun checkCircleArea() {
        if ((currentLatitude <= NSU_LATITUDE + LATITUDE_SPREAD) &&
            (currentLatitude >= NSU_LATITUDE - LATITUDE_SPREAD) &&
            (currentLongitude <= NSU_LONGITUDE + LONGITUDE_SPREAD) &&
            (currentLongitude >= NSU_LONGITUDE - LONGITUDE_SPREAD)
        ) {
            Log.d("Debug", "sendNotification")
            sendNotification()
        }
    }

    private fun startTimer() {
        handler.postDelayed(runnable, SECS * 1000L)
    }

    private fun stopTimer() {
        handler.removeCallbacks(runnable)
    }

    private fun getLastLocation() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            val location: Location? = task.result
            if (location == null) {
                newLocationData()
            } else {
                currentLatitude = location.latitude
                currentLongitude = location.longitude
                checkCircleArea()

                Log.d(
                    "Debug:",
                    "You Current Location is : Long: " + location.longitude + " , Lat: " + location.latitude
                )
            }
        }
    }

    private fun newLocationData() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location? = locationResult.lastLocation
            Log.d(
                "Debug:",
                "You Last Location is : Long: " + lastLocation!!.longitude + " , Lat: " + lastLocation.latitude
            )
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

    companion object {
        const val SECS = 1
        const val DUSORAN_LATITUDE = 54.836294
        const val DUSORAN_LONGITUDE = 83.102099

        const val NSU_LATITUDE = 54.843326
        const val NSU_LONGITUDE = 83.089725

        const val HOME_LATITUDE = 54.858454
        const val HOME_LONGITUDE = 83.113842

        // 0.001 = 111 m
        const val LATITUDE_SPREAD = 0.002
        const val LONGITUDE_SPREAD = 0.002
    }
}