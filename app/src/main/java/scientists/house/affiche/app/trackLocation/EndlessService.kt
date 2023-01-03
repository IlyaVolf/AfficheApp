package scientists.house.affiche.app.trackLocation

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.os.PowerManager
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import scientists.house.affiche.app.R
import scientists.house.affiche.app.screens.main.MainActivity

// ---------ГАВНОКОД, НЕ РЕКОМЕНДУЕТСЯ ДЛЯ ПРОСМОТРА СЛАБОНЕРВНЫМ  ---------------------
// --------- ПЕРЕД ПРОСМОТРОМ ХОРОШЕНЬКО ПОДУМАЙ -------------------------------------

class EndlessService : Service() {

    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false

    override fun onBind(intent: Intent): IBinder? {
        log("Some component want to bind with the service")
        // We don't provide binding, so return null
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand executed with startId: $startId")
        if (intent != null) {
            val action = intent.action
            log("using an intent with action $action")
            when (action) {
                Actions.START.name -> startService()
                Actions.STOP.name -> stopService()
                else -> log("This should never happen. No action in the received intent")
            }
        } else {
            log(
                "with a null intent. It has been probably restarted by the system."
            )
        }
        // by returning this we make sure the service is restarted if the system kills the service
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        log("The service has been created")

        createNotificationChannel()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Дом ученых")
            .setContentText("Посетите наше предстоящие мероприятие. Вам понравится!")
            .setSmallIcon(R.drawable.ic_affiche)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager = NotificationManagerCompat.from(this)

        val notification = createForegroundNotification()
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        log("The service has been destroyed")
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show()
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        val restartServiceIntent = Intent(applicationContext, EndlessService::class.java).also {
            it.setPackage(packageName)
        };
        val restartServicePendingIntent: PendingIntent = PendingIntent.getService(this, 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        applicationContext.getSystemService(Context.ALARM_SERVICE);
        val alarmService: AlarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager;
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartServicePendingIntent);
    }

    private fun startService() {
        if (isServiceStarted) return
        log("Starting the foreground service task")
        isServiceStarted = true
        setServiceState(this, ServiceState.STARTED)

        // we need this lock so our service gets not affected by Doze Mode
        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "EndlessService::lock").apply {
                    acquire()
                }
            }

        // we're starting a loop in a coroutine
        GlobalScope.launch(Dispatchers.IO) {
            while (isServiceStarted) {
                launch(Dispatchers.IO) {
                    pingLocation()
                }
                delay(SECS * 1000)
            }
            log("End of the loop for the service")
        }
    }

    private fun stopService() {
        log("Stopping the foreground service")
        try {
            wakeLock?.let {
                if (it.isHeld) {
                    it.release()
                }
            }
            stopForeground(true)
            stopSelf()
        } catch (e: Exception) {
            log("Service stopped without being started: ${e.message}")
        }
        isServiceStarted = false
        setServiceState(this, ServiceState.STOPPED)
    }

    private fun pingLocation() {
        Log.d("Debug", "pingLocation()")
//        getLastLocation()
        getLocation()
    }

    private fun createForegroundNotification(): Notification {
        val notificationChannelId = "ENDLESS SERVICE CHANNEL"

        // depending on the Android API that we're dealing with we will have
        // to use a specific method to create the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                notificationChannelId,
                "Endless Service notifications channel",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = "Endless Service channel"
                it.enableLights(true)
                it.lightColor = Color.RED
                it.enableVibration(true)
                it.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent: PendingIntent = Intent(this, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, 0)
        }

        val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
            this,
            notificationChannelId
        ) else Notification.Builder(this)

        return builder
            .setContentTitle("Афиша service работает в background")
            .setContentText("Информация, необходимая для отладки работы в background")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker("Ticker text")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }


    // ----------------------------------------------------------
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val channelId = "channelID"
    private val channelName = "channelName"

    private lateinit var notification: Notification
    private lateinit var notificationManager: NotificationManagerCompat

    private var currentLatitude: Double = 0.0
    private var currentLongitude: Double = 0.0

    private fun checkCircleArea() {
        if ((currentLatitude <= DUSORAN_LATITUDE + LATITUDE_SPREAD) &&
            (currentLatitude >= DUSORAN_LATITUDE - LATITUDE_SPREAD) &&
            (currentLongitude <= DUSORAN_LONGITUDE + LONGITUDE_SPREAD) &&
            (currentLongitude >= DUSORAN_LONGITUDE - LONGITUDE_SPREAD)
        ) {
            Log.d("Debug", "sendNotification")
            sendNotification()
        }
    }

    private fun getLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                currentLatitude = location.latitude
                currentLongitude = location.longitude
                checkCircleArea()
                log("location $location")
                log("You Current Location is : Long: " + location.longitude + " , Lat: " + location.latitude)
            }
    }

    private fun getLastLocation() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            val location: Location? = task.result

            log("location $location")

            if (location == null) {
                newLocationData()
            } else {
                currentLatitude = location.latitude
                currentLongitude = location.longitude
                checkCircleArea()

                log("You Current Location is : Long: " + location.longitude + " , Lat: " + location.latitude)
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
            val lastLocation: Location = locationResult.lastLocation
            log("You Last Location is : Long: " + lastLocation.longitude + " , Lat: " + lastLocation.latitude)
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
        const val SECS = 60L
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