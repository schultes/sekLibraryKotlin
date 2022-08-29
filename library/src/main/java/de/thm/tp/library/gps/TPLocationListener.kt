package de.thm.tp.library.gps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.content.ContextCompat

/**
 * This class provides methods for location tracking of a user
 */

/**
 * Initialize a TPLocationListener with a passed change distance value.
 *
 * @param context           Android-ApplicationContext
 * @param distanceInMeters  Necessary distance that must be exceeded before changes are returned via the callback
 */
class TPLocationListener(
    private val context: Context,
    private val distanceInMeters: Double
) : LocationListener {

    private val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var isListeningOnce = false
    private var isListening = false
    private var lastKnownLocation: TPLocation? = null

    private var callback: ((location: TPLocation) -> Unit)? = null

    /**
     * Initialize a TPLocationListener with a fixed distance value of 1.0 meter
     * (Need for Swift equivalence)
     *
     * @param context   Android-ApplicationContext
     */
    constructor(context: Context) : this(
        context,
        1.0
    )

    /**
     * Setting a callback function via which TPLocation objects can be returned in the event of a position change.
     *
     * @param callback  Callback-Function that can be used to return a TPLocation object.
     *
     * @see TPLocation
     */
    fun setCallback(callback: (location: TPLocation) -> Unit) {
        this.callback = callback
    }

    /**
     * If permission is granted, Location Listening is started and waits for position updates.
     * Location Listening will not be started and no error message will be issued without the appropriate authorization.
     *
     * @see TPLocation
     *
     * (In Kotlin the permission `android.permission.ACCESS_FINE_LOCATION` is required)
     */
    @SuppressLint("MissingPermission")
    fun startListeningForUpdates() {
        if (hasPermission(context)) {
            if (!isListening) {
                this.isListening = true
                this.locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000,
                    this.distanceInMeters.toFloat(),
                    this
                )
            } else {
                this.isListeningOnce = false
                if (this.lastKnownLocation != null) {
                    this.callback?.let { it(this.lastKnownLocation!!) }
                }
            }
        }
    }

    /**
     * Searches the current position and returns it via the callback
     *
     * @see TPLocation
     */
    fun getCurrentLocation() {
        this.isListeningOnce = true
        this.startListeningForUpdates()
    }

    /**
     * Stops all location updates
     */
    fun stopListeningForUpdates() {
        this.isListening = false
        this.isListeningOnce = false
        this.locationManager.removeUpdates(this)
    }

    /**
     * If actively waiting for position changes, this function returns `true`,
     * it will wait for changes as soon as the method `startListeningForUpdates()`
     * has been called and has not been stopped by the method `stopListeningForUpdates()`.
     *
     * @return `true` when waiting for position changes, otherwise `false`
     */
    fun isListeningForUpdates(): Boolean {
        return this.isListening
    }

    /**
     * Returns the last known position,
     * If no position has been determined yet, `null` is returned
     *
     * @return TPLocation or null
     *
     * @see TPLocation
     */
    fun getLastKnownLocation(): TPLocation? {
        return this.lastKnownLocation
    }

    companion object {

        /**
         * Checks if the permission `android.permission.ACCESS_FINE_LOCATION` has been granted.
         *
         * @param context Android-ApplicationContext
         *
         * @return `true` if the permissions have been granted, otherwise `false`
         */
        fun hasPermission(context: Context): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * This method is used internally and should not be called or overridden by the developer.
     *
     * Overrides the onLocationChanged method of the LocationListener interface and is called by the system on LocationUpdates.
     *
     * @param location  Current position passed from the system
     *
     * @see TPLocation
     */
    override fun onLocationChanged(location: Location) {
        this.lastKnownLocation = TPLocation(
            location.time.toDouble(),
            location.latitude,
            location.longitude,
            location.altitude,
            location.speed.toDouble(),
            location.accuracy.toDouble()
        )

        this.callback?.let {
            it(
                this.lastKnownLocation!!
            )
        }

        if (this.isListeningOnce) {
            this.stopListeningForUpdates()
        }
    }
}
