package de.thm.tp.library.gps

/**
 * This class holds basic position-related data
 *
 * @param time          Time in ms calculated from 01.01.1970 00:00 am
 * @param latitude      Latitude
 * @param longitude     Longitude
 * @param altitude      Altitude
 * @param speed         Instantaneous velocity in m/s
 * @param accuracy      Position Accuracy
 */
class TPLocation(
    val time: Double,
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val speed: Double,
    val accuracy: Double
) {

    /**
     *Returns the object variables as a complete string
     */
    override fun toString(): String {
        return "time: $time; latitude: $latitude; longitude: $longitude; altitude: $altitude; speed: $speed; accuracy: $accuracy"
    }
}
