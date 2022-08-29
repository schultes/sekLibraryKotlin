package de.thm.tp.library.json

import org.json.JSONArray
import org.json.JSONException
import java.util.*

/**
 * This class represents a JSON-Array and provides various getter/setter.
 *
 * @param objects Accepts a list of TPJSONObjects (optional)
 *
 * @see TPJSONObject
 */
class TPJSONArray(val data: org.json.JSONArray = org.json.JSONArray()) {

    constructor(objects: List<Any>) : this(
        JSONArray(objects.map { obj ->
            if (obj is TPJSONObject) {
                return@map obj.data
            } else {
                return@map obj
            }
        })
    )

    /**
     * Returns the length of the given TPJSONArray
     *
     * @return Length of the given TPJSONArray
     */
    fun length() = data.length()

    /**
     * Returns a TPJSONObject for a given index
     *
     * @param index Index of the requested object
     *
     * @return Object of type TPJSONObject
     *
     * @see TPJSONArray
     */
    @Throws(JSONException::class)
    fun getJSONObject(index: Int): TPJSONObject = TPJSONObject(data.getJSONObject(index))


    /**
     * Returns a String for a given index
     *
     * @param index Index of the requested string
     *
     * @return Value of type String or null
     */
     fun getString(index: Int) =data.get(index) as? String ?: null

    /**
     * Returns a value for a given index
     *
     * @param index Index of the requested value
     *
     * @return Value of type Any or null
     */
     @Throws(JSONException::class)
     fun get(index: Int): Any = data.get(index)


    /**
     * Creates a new array populated with the results of calling a provided function on every element (TPJSONObject) in the calling array.
     *
     * @param mapCallback Callback function that should be executed on every element
     *
     * @return Newly created list.
     *
     * @see TPJSONObject
     */
    @Throws(JSONException::class)
    fun flatMap(mapCallback: (TPJSONObject) -> TPJSONObject?): TPJSONArray {
        val list = ArrayList<TPJSONObject>()
        for (i in 0 until data.length()) {
            val newObject = mapCallback(getJSONObject(i))
            if (newObject != null) {
                list.add(newObject)
            }
        }
        return TPJSONArray(list.toList())
    }

    /**
     * Creates a new array populated with the results of calling a provided function on every element in the calling array.
     *
     * @param mapCallback Callback function that should be executed on every element
     *
     * @return Newly created list.
     */
    @Throws(JSONException::class)
    fun map(mapCallback: (Any) -> Any): TPJSONArray {
        val list = ArrayList<Any>()
        for (i in 0..length() - 1) {
            list.add(mapCallback(data.get(i)))
        }
        return TPJSONArray(list.toList())
    }

    @Throws(JSONException::class)
    internal fun <U> mapList(mapCallback: (Any) -> U): List<U> {
        val list = ArrayList<U>()
        for (i in 0..length() - 1) {
            list.add(mapCallback(data.get(i)))
        }
        return list
    }

    /**
     * Returns the current array as a list of a given generic parameter.
     *
     * @return List of the given generic parameter
     *
     * @see TPJSONObject
     */
    @Throws(JSONException::class)
    fun <T> asList(): List<T> {
        val list = mutableListOf<T>()
        for (i in 0 until data.length()) {
            val obj = data.get(i)
            when (obj) {
                is org.json.JSONObject -> list.add(TPJSONObject(data = obj).asMap<Any>() as T)
                is org.json.JSONArray -> list.add(TPJSONArray(data = obj).asList<Any>() as T)
                else -> list.add(obj as T)
            }
        }
        return list
    }

    /**
     * Returns the current array as a list of TPJSONObjects
     *
     * @return List of TPJSONObjects
     *
     * @see TPJSONObject
     */
    @Throws(JSONException::class)
    fun asJSONList(): List<TPJSONObject> {
        val list = mutableListOf<TPJSONObject>()
        for (i in 0 until data.length()) {
            list.add(getJSONObject(i))
        }
        return list
    }

    /**
     * Adds a TPJSONObject to the given TPJSONArray
     *
     * @param object TPJSONObject to be added
     */
    fun add(`object`: TPJSONObject) {
        data.put(`object`.data)
    }

    /**
     * Adds a list of TPJSONObjects to the given TPJSONArray
     *
     * @param objects List of TPJSONObjects to be added
     */
    fun addAll(objects: List<TPJSONObject>) {
        objects.forEach { data.put(it.data) }
    }

    /**
     * Adds a list of Strings to the given TPJSONArray
     *
     * @param objects List of Strings to be added
     */
    fun addAllString(objects: List<String>) {
        objects.forEach { data.put(it) }
    }

    /**
     * Adds a list of Integers to the given TPJSONArray
     *
     * @param objects List of Integers to be added
     */
    fun addAllInt(objects: List<Int>) {
        objects.forEach { data.put(it) }
    }

    /**
     * Adds an Integer to the given TPJSONArray
     *
     * @param value Integer to be added
     */
    fun addInt(value: Int) {
        data.put(value)
    }

    /**
     * Adds a String to the given TPJSONArray
     *
     * @param value String to be added
     */
    fun addString(value: String) {
        data.put(value)
    }

    /**
     * Adds a Double to the given TPJSONArray
     *
     * @param value Double to be added
     */
    fun addDouble(value: Double) {
        data.put(value)
    }

    /**
     * Adds a Boolean to the given TPJSONArray
     *
     * @param value Boolean to be added
     */
    fun addBoolean(value: Boolean) {
        data.put(value)
    }

    /**
     * Returns the given TPJSONArray as Byte-Array
     */
    fun toData() = toString().toByteArray()

    /**
     * Returns the given TPJSONArray as String
     */
    override fun toString() = data.toString()
}
