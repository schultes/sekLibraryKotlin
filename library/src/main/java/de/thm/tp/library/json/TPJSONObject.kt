package de.thm.tp.library.json



import org.json.JSONException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * This class represents a JSON-Object and provides various getter/setter.
 *
 * @param string Accepts a valid JSON-String (optional)
 */
class TPJSONObject(val data: org.json.JSONObject = org.json.JSONObject()) {

    constructor(string: String) : this(data = org.json.JSONObject(string))

    /**
     * Inspects if key exists in JSON-Object
     *
     * @param key To be checked key
     *
     * @return Boolean
     */
    fun has(key: String) = !data.isNull(key)

    /**
     * Returns a value of type Any for a given key, if it exists. Otherwise returns null.
     *
     * @param key Name of the key
     *
     * @return Returns the value of type Any or null
     */
    fun getAny(key: String): Any? =
            if (data.isNull(key)) null else data.opt(key)


    /**
     * Returns a value of type Integer for a given key, if it exists. Otherwise returns null.
     *
     * @param key Name of the key
     *
     * @return Returns the value of type Integer or null
     */
    fun getInt(key: String): Int? {
        val obj = data.opt(key)
        if (obj is Int) {
            return obj
        } else if (obj is Number) {
            return obj.toInt()
        }

        return null
    }

    /**
     * Returns a value of type Double for a given key, if it exists. Otherwise returns null.
     *
     * @param key Name of the key
     *
     * @return Returns the value of type Double or null
     */
    fun getDouble(key: String): Double? {
        val obj = data.opt(key)
        if (obj is Double) {
            return obj
        } else if (obj is Number) {
            return obj.toDouble()
        }

        return null
    }

    /**
     * Returns a value of type Boolean for a given key, if it exists. Otherwise returns null.
     *
     * @param key Name of the key
     *
     * @return Returns the value of type Boolean or null
     */
    fun getBoolean(key: String): Boolean? = data.opt(key) as? Boolean

    /**
     * Returns a value of type String for a given key, if it exists. Otherwise returns null.
     *
     * @param key Name of the key
     *
     * @return Returns the value of type String or null
     */
    fun getString(key: String) = data.opt(key) as? String

    /**
     * Returns a value of type Date for a given key, if it exists. Otherwise returns null.
     *
     * @param key Name of the key
     *
     * @return Returns the value of type Date or null
     */
    fun getDate(key: String): Date? {
        val obj = data.opt(key)
        if (obj is String) {
            return try {
                dateFormatter.timeZone = java.util.TimeZone.getTimeZone("utc")
                dateFormatter.parse(obj)
            } catch (e: ParseException) {
                null
            }
        }
        return null
    }

    /**
     * Returns a value of type TPJSONObject for a given key, if it exists. Otherwise returns null.
     *
     * @param key Name of the key
     *
     * @return Returns the value of type TPJSONObject or null
     *
     * @see TPJSONObject
     */
    fun getJSONObject(key: String): TPJSONObject? {
        if (data.isNull(key)) {
            return null
        }
        val obj = data.opt(key)
        if (obj is org.json.JSONObject) {
            return TPJSONObject(obj)
        }

        return null
    }

    /**
     * Returns a value of type TPJSONArray for a given key, if it exists. Otherwise returns null.
     *
     * @param key Name of the key
     *
     * @return Returns the value of type TPJSONArray or null
     *
     * @see TPJSONArray
     */
    fun getJSONArray(key: String): TPJSONArray? {
        val obj = data.opt(key)
        if (obj is org.json.JSONArray) {
            return TPJSONArray(obj)
        }

        return null
    }

    /**
     * Returns a list of Strings for a given key, if it exists. Otherwise returns null.
     *
     * @param key Name of the key
     *
     * @return Returns as list of Strings or null
     */
    fun getStringArray(key: String): List<String>? {
        try {
            val array = getJSONArray(key)
            return array?.mapList { item -> item as String }

        } catch (je: JSONException) {
            return null
        }
    }

    /**
     * Returns a list of Integers for a given key, if it exists. Otherwise returns null.
     *
     * @param key Name of the key
     *
     * @return Returns as list of Integers or null
     */
    fun getIntArray(key: String): List<Int>? {
        try {
            val array = getJSONArray(key)
            return array?.mapList { item -> item as Int }
        } catch (je: JSONException) {
            return null
        }
    }

    /**
     * Returns a list of Any for a given key, if it exists. Otherwise returns null.
     *
     * @param key Name of the key
     *
     * @return Returns as list of Any or null
     */
    fun getAnyArray(key: String): List<Any>? {
        try {
            val array = getJSONArray(key)
            return array?.mapList { it }
        } catch (je: JSONException) {
            return null
        }
    }

    /**
     * Converts the given TPJSONObject to a Map.
     *
     * @return Map of TPJSONObject
     */
    @Throws(JSONException::class)
    fun <T> asMap(): Map<String, T> {
        val map = mutableMapOf<String, T>()
        val iterator = data.keys()
        while (iterator.hasNext()) {
            val key = iterator.next()
            var obj = data.get(key)
            map[key] = when (obj) {
                is org.json.JSONArray -> TPJSONArray(obj).asList<Any>()
                is org.json.JSONObject -> TPJSONObject(obj).asMap<Any>()
                else -> obj
            } as T
        }

        return map
    }

    /**
     * Sets value of type Any for a given key
     *
     * @param key Key for which the value is to be set
     * @param value Value to be set
     */
    fun setAny(key: String, value: Any?) {
        data.put(key, value)
    }

    /**
     * Sets value of type Integer for a given key
     *
     * @param key Key for which the value is to be set
     * @param value Value to be set
     */
    fun setInt(key: String, value: Int?) {
        data.put(key, value)
    }

    /**
     * Sets value of type Double for a given key
     *
     * @param key Key for which the value is to be set
     * @param value Value to be set
     */
    fun setDouble(key: String, value: Double?) {
        data.put(key, value)
    }

    /**
     * Sets value of type String for a given key
     *
     * @param key Key for which the value is to be set
     * @param value Value to be set
     */
    fun setString(key: String, value: String?) {
        data.put(key, value)
    }


    /**
     * Sets value of type Boolean for a given key
     *
     * @param key Key for which the value is to be set
     * @param value Value to be set
     */
    fun setBoolean(key: String, value: Boolean?) {
        data.put(key, value)
    }

    /**
     * Sets value of type TPJSONObject for a given key
     *
     * @param key Key for which the value is to be set
     * @param value Value to be set
     */
    fun setObject(key: String, value: TPJSONObject?) {
        if (value != null) {
            data.put(key, value.data)
        } else {
            data.remove(key)
        }
    }

    /**
     * Sets a list of TPJSONObjects for a given key
     *
     * @param key Key for which the value is to be set
     * @param value Value to be set
     */
    fun setArray(key: String, value: List<TPJSONObject>?) {
        if (value != null) {
            val array = TPJSONArray()
            array.addAll(value)
            data.put(key, array.data)
        } else {
            data.remove(key)
        }
    }

    /**
     * Sets a TPJSONArray for a given key
     *
     * @param key Key for which the value is to be set
     * @param value Value to be set
     *
     * @see TPJSONArray
     */
    fun setJSONArray(key: String, value: TPJSONArray?) {
        if (value != null) {
            data.put(key, value.data)
        } else {
            data.remove(key)
        }
    }

    /**
    * Sets a list of Strings for a given key
    *
    * @param key Key for which the value is to be set
    * @param value Value to be set
    */
    fun setStringArray(key: String, value: List<String>?) {
        if (value != null) {
            val array = TPJSONArray()
            array.addAllString(value)
            data.put(key, array.data)
        } else {
            data.remove(key)
        }
    }

    /**
     * Sets a list of Integers for a given key
     *
     * @param key Key for which the value is to be set
     * @param value Value to be set
     */
    fun setIntArray(key: String, value: List<Int>?) {
        if (value != null) {
            val array = TPJSONArray()
            array.addAllInt(value)
            data.put(key, array.data)
        } else {
            data.remove(key)
        }
    }

    /**
     * Sets a value of type Date for a given key
     *
     * @param key Key for which the value is to be set
     * @param value Value to be set
     */
    fun setDate(key: String, value: Date?) {
        if (value == null) {
            data.remove(key)
            return
        }

        dateFormatter.timeZone = java.util.TimeZone.getTimeZone("utc")
        data.put(key, dateFormatter.format(value))
    }

    /**
     * Returns the given TPJSONObject as Byte-Array
     */
    fun toData() = toString().toByteArray()

    /**
     * Returns the given TPJSONObject as String
     */
    override fun toString() = data.toString().replace("\\/", "/")

    companion object {
        /**
         * Formats Date-Format for idenitcal Date-Format
         */
        private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    }
}
