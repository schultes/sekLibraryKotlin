package de.thm.tp.library.random

import kotlin.random.Random

/**
 * Generates a random number or boolean
 */
class TPRandom {
    companion object {
        /**
         * Generates a random Int between the given range
         *
         * @param from            Lower bound
         * @param to              Upper bound
         *
         * @return Int
         */
        fun int(from: Int, to: Int): Int {
            return Random.nextInt(from, to)
        }

        /**
         * Generates a random Double between the given range
         *
         * @param from            Lower bound
         * @param to              Upper bound
         *
         * @return Double
         */
        fun double(from: Double, to: Double): Double {
            return Random.nextDouble(from, to)
        }

        /**
         * Generates a random Boolean
         *
         * @return Boolean
         */
        fun boolean(): Boolean {
            return Random.nextBoolean()
        }
    }
}