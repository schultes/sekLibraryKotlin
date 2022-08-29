package de.thm.tp.library.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import de.thm.tp.library.sqlite.comfort.TPSQLiteCondition
import de.thm.tp.library.sqlite.comfort.queryBuilder.TPSQLiteQuery
import de.thm.tp.library.sqlite.comfort.TPSQLiteTable
import kotlin.collections.ArrayList
import kotlin.jvm.internal.Reflection
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.memberProperties

/**
 * This class keeps the connection to the local SQLite database
 */
class TPSQLite private constructor(var context: Context, var dbName: String) {

    @PublishedApi
    internal var dbHelper: DBHelper = DBHelper(context, dbName)

    public var comfort: TPSQLiteComfort = TPSQLiteComfort(dbHelper)

    companion object {

        @Volatile private var INSTANCE: TPSQLite? = null

        /**
         * Creates a connection to a local SQLite database
         *
         * @param context           Android-ApplicationContext
         * @param dbName            Name of the database
         */
        fun open(context: Context, dbName: String): TPSQLite =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TPSQLite(context, dbName).also { INSTANCE = it }
            }
    }

    /**
     * Inserts one or more objects of a specified model into the table in the local SQLite database
     *
     * @param tableName       Name of the table
     * @param data            One or more objects of a specified model
     * @param callback        Callback function via which an error message can be returned
     *
     * @see TPSQLiteEntity
     */
    inline fun <reified T: TPSQLiteEntity> insert(
        tableName: String,
        vararg data: T,
        callback: (error: String?) -> Unit
    ) {
        val db = dbHelper.writableDatabase
        val members = T::class.declaredMembers

        data.forEach { obj ->
            val contentValues = ContentValues()

            members.forEach { member ->
                when (member.returnType.toString()) {
                    "kotlin.Boolean" -> contentValues.put(member.name, member.call(obj) as Boolean)
                    "kotlin.Byte" -> contentValues.put(member.name, member.call(obj) as Byte)
                    "kotlin.ByteArray" -> contentValues.put(
                        member.name,
                        member.call(data) as ByteArray
                    )
                    "kotlin.Double" -> contentValues.put(member.name, member.call(obj) as Double)
                    "kotlin.Float" -> contentValues.put(member.name, member.call(obj) as Float)
                    "kotlin.Int" -> contentValues.put(member.name, member.call(obj) as Int)
                    "kotlin.Long" -> contentValues.put(member.name, member.call(obj) as Long)
                    "kotlin.Short" -> contentValues.put(member.name, member.call(obj) as Short)
                    "kotlin.String" -> contentValues.put(member.name, member.call(obj) as String)
                }
            }
            try {
                db.replace(tableName, null, contentValues)
                callback(null)
            } catch (e: SQLiteException) {
                callback(e.message.toString())
            }
        }
    }

    // READ

    /**
     * Queries a result set from the local SQLite database
     *
     * @param data            Specified model for the result set
     * @param query           String that contains the SQL query
     * @param selectionArgs   Optional array of strings, which contains the binding arguments for the condition (note the order!)
     * @param callback        Callback function via which an error message and an optional result set of the specified model can be returned
     *
     * @see TPSQLiteEntity
     */
    fun <T: TPSQLiteEntity> query(
        data: Class<T>,
        query: String,
        selectionArgs: Array<String>? = null,
        callback: (result: ArrayList<T>?, error: String?) -> Unit
    ) {
        val result: ArrayList<T> = arrayListOf()
        val members = Reflection.createKotlinClass(data).declaredMembers
        val db = dbHelper.writableDatabase

        try {
            val c = db.rawQuery(query, selectionArgs)

            if (c.moveToFirst()) {
                do {
                    val kClass = Class.forName(data.name).kotlin
                    val instance = kClass.createInstance()

                    members.forEach { member ->
                        val property = kClass.memberProperties.filterIsInstance<KMutableProperty<*>>().firstOrNull { it.name == member.name }
                        if (property != null && c.getColumnIndex(member.name).toString() != "-1") {
                            when (member.returnType.toString()) {
                                "kotlin.Boolean" -> property?.setter?.call(instance, c.getInt(c.getColumnIndex(member.name)) > 0)
                                "kotlin.Boolean?" -> property?.setter?.call(instance, c.getInt(c.getColumnIndex(member.name)) > 0)
                                "kotlin.ByteArray" -> property?.setter?.call(instance, c.getBlob(c.getColumnIndex(member.name)))
                                "kotlin.ByteArray?" -> property?.setter?.call(instance, c.getBlob(c.getColumnIndex(member.name)))
                                "kotlin.Double" -> property?.setter?.call(instance, c.getString(c.getColumnIndex(member.name)))
                                "kotlin.Double?" -> property?.setter?.call(instance, c.getString(c.getColumnIndex(member.name)))
                                "kotlin.Float" -> property?.setter?.call(instance, c.getFloat(c.getColumnIndex(member.name)))
                                "kotlin.Float?" -> property?.setter?.call(instance, c.getFloat(c.getColumnIndex(member.name)))
                                "kotlin.Int" -> property?.setter?.call(instance, c.getInt(c.getColumnIndex(member.name)))
                                "kotlin.Int?" -> property?.setter?.call(instance, c.getInt(c.getColumnIndex(member.name)))
                                "kotlin.Long" -> property?.setter?.call(instance, c.getLong(c.getColumnIndex(member.name)))
                                "kotlin.Long?" -> property?.setter?.call(instance, c.getLong(c.getColumnIndex(member.name)))
                                "kotlin.Short" -> property?.setter?.call(instance, c.getShort(c.getColumnIndex(member.name)))
                                "kotlin.Short?" -> property?.setter?.call(instance, c.getShort(c.getColumnIndex(member.name)))
                                "kotlin.String" -> property?.setter?.call(instance, c.getString(c.getColumnIndex(member.name)))
                                "kotlin.String?" -> property?.setter?.call(instance, c.getString(c.getColumnIndex(member.name)))
                            }
                        }
                    }
                    result.add(instance as T)
                } while (c.moveToNext())
                c.close()
                callback(result, null)
            }
        } catch (e: SQLiteException) {
            callback(null, e.message.toString())
        }
    }

    // UPDATE
    /**
     * Updates one or more rows from a table in the local SQLite database
     *
     * @param tableName       One or more table names
     * @param data            Object of a specified model, that contains the new values of data to be changed
     * @param condition       Condition, which specifies the rows to be updated from the table
     * @param selectionArgs   Optional array of strings, which contains the binding arguments for the condition (note the order!)
     * @param callback        Callback function via which an error message can be returned
     *
     * @see TPSQLiteEntity
     * @see TPSQLiteCondition
     */
    inline fun <reified T: TPSQLiteEntity> update(
        tableName: String,
        data: T,
        condition: String,
        selectionArgs: Array<String>? = null,
        callback: (error: String?) -> Unit
    ) {
        val db = dbHelper.writableDatabase
        val members = T::class.declaredMembers
        val contentValues = ContentValues()

        members.forEach { member ->
            when (member.returnType.toString()) {
                "kotlin.Boolean" -> contentValues.put(member.name, member.call(data) as Boolean)
                "kotlin.Byte" -> contentValues.put(member.name, member.call(data) as Byte)
                "kotlin.ByteArray" -> contentValues.put(member.name, member.call(data) as ByteArray)
                "kotlin.Double" -> contentValues.put(member.name, member.call(data) as Double)
                "kotlin.Float" -> contentValues.put(member.name, member.call(data) as Float)
                "kotlin.Int" -> contentValues.put(member.name, member.call(data) as Int)
                "kotlin.Long" -> contentValues.put(member.name, member.call(data) as Long)
                "kotlin.Short" -> contentValues.put(member.name, member.call(data) as Short)
                "kotlin.String" -> contentValues.put(member.name, member.call(data) as String)
            }
        }
        try {
            db.update(tableName, contentValues, condition, selectionArgs)
            callback(null)
        } catch (e: SQLiteException) {
            callback(e.message.toString())
        }
    }

    // DELETE
    /**
     * Removes one or more tables in the local SQLite database
     *
     * @param tableName       One or more table names
     * @param callback        Callback function via which an error message can be returned
     */
    fun dropTable(
        vararg tableName: String,
        callback: (error: String?) -> Unit
    ) {
        tableName.forEach { t ->
            val db = dbHelper.writableDatabase
            try {
                db.execSQL("DROP TABLE IF EXISTS $t")
                callback(null)
            } catch (e: SQLiteException) {
                callback(e.message.toString())
            }
        }
    }

    /**
     * Removes one or more rows from a table in the local SQLite database
     *
     * @param tableName       Name of the table
     * @param condition       Optional condition, which specifies the rows to be removed from the table
     * @param selectionArgs   Optional array of strings, which contains the binding arguments for the condition (note the order!)
     * @param callback        Callback function via which an error message can be returned
     *
     * @see TPSQLiteCondition
     */
    fun delete(
        tableName: String,
        condition: String? = null,
        selectionArgs: Array<String>? = null,
        callback: (error: String?) -> Unit
    ) {
        val db = dbHelper.writableDatabase

        try {
            db.delete(tableName, condition, selectionArgs)
            callback(null)
        } catch (e: SQLiteException) {
            callback(e.message.toString())
        }
    }

    /**
     * Execute a single SQL statement that is NOT a SELECT/INSERT/UPDATE/DELETE
     *
     * @param sql             String which contains the SQL statement
     * @param selectionArgs   Optional array of strings, which contains the binding arguments for the condition (note the order!)
     * @param callback        Callback function via which an error message can be returned
     *
     * @see TPSQLiteCondition
     */
    fun execSQL(
        sql: String,
        selectionArgs: Array<String> = arrayOf(),
        callback: (error: String?) -> Unit
    ) {
        val db = dbHelper.writableDatabase

        try {
            db.execSQL(sql, selectionArgs)
            callback(null)
        } catch (e: SQLiteException) {
            callback(e.message.toString())
        }
    }

    class TPSQLiteComfort internal constructor(var dbHelper: DBHelper) {

        /**
         * [Comfort function]
         * Creates one or more tables in the local SQLite database
         *
         * @param table           One or more objects of type TPSQLiteTable
         * @param callback        Callback function via which an error message can be returned
         *
         * @see TPSQLiteTable
         */
        fun createTable(
            vararg table: TPSQLiteTable,
            callback: (error: String?) -> Unit
        ) {
            val db = dbHelper.writableDatabase

            table.forEach { t ->
                try {
                    db.execSQL(t.createStatement())
                    callback(null)
                } catch (e: SQLiteException) {
                    callback(e.message.toString())
                }
            }
        }

        /**
         * [Comfort function]
         * Queries a result set from the local SQLite database
         *
         * @param data            Specified model for the result set
         * @param query           Object of type TPSQLiteQuery, that contains the SQL query
         * @param selectionArgs   Optional array of strings, which contains the binding arguments for the condition (note the order!)
         * @param callback        Callback function via which an error message and an optional result set of the specified model can be returned
         *
         * @see TPSQLiteEntity
         * @see TPSQLiteQuery
         */
        fun <T: TPSQLiteEntity> query(
            data: Class<T>,
            query: TPSQLiteQuery,
            selectionArgs: Array<String>? = null,
            callback: (result: ArrayList<T>?, error: String?) -> Unit
        ) {
            val result: ArrayList<T> = ArrayList()
            val members = Reflection.createKotlinClass(data).declaredMembers
            val db = dbHelper.writableDatabase
            val query = query.toSQL()

            try {
                val c = db.rawQuery(query, selectionArgs)

                if (c.moveToFirst()) {
                    do {
                        val kClass = Class.forName(data.name).kotlin
                        val instance = kClass.createInstance()

                        members.forEach { member ->
                            val property = kClass.memberProperties.filterIsInstance<KMutableProperty<*>>().firstOrNull { it.name == member.name }
                            if (c.getColumnIndex(member.name).toString() != "-1") {
                                when (member.returnType.toString()) {
                                    "kotlin.Boolean" -> property?.setter?.call(instance, c.getInt(c.getColumnIndex(member.name)) > 0)
                                    "kotlin.Boolean?" -> property?.setter?.call(instance, c.getInt(c.getColumnIndex(member.name)) > 0)
                                    "kotlin.ByteArray" -> property?.setter?.call(instance, c.getBlob(c.getColumnIndex(member.name)))
                                    "kotlin.ByteArray?" -> property?.setter?.call(instance, c.getBlob(c.getColumnIndex(member.name)))
                                    "kotlin.Double" -> property?.setter?.call(instance, c.getString(c.getColumnIndex(member.name)))
                                    "kotlin.Double?" -> property?.setter?.call(instance, c.getString(c.getColumnIndex(member.name)))
                                    "kotlin.Float" -> property?.setter?.call(instance, c.getFloat(c.getColumnIndex(member.name)))
                                    "kotlin.Float?" -> property?.setter?.call(instance, c.getFloat(c.getColumnIndex(member.name)))
                                    "kotlin.Int" -> property?.setter?.call(instance, c.getInt(c.getColumnIndex(member.name)))
                                    "kotlin.Int?" -> property?.setter?.call(instance, c.getInt(c.getColumnIndex(member.name)))
                                    "kotlin.Long" -> property?.setter?.call(instance, c.getLong(c.getColumnIndex(member.name)))
                                    "kotlin.Long?" -> property?.setter?.call(instance, c.getLong(c.getColumnIndex(member.name)))
                                    "kotlin.Short" -> property?.setter?.call(instance, c.getShort(c.getColumnIndex(member.name)))
                                    "kotlin.Short?" -> property?.setter?.call(instance, c.getShort(c.getColumnIndex(member.name)))
                                    "kotlin.String" -> property?.setter?.call(instance, c.getString(c.getColumnIndex(member.name)))
                                    "kotlin.String?" -> property?.setter?.call(instance, c.getString(c.getColumnIndex(member.name)))
                                }
                            }
                        }
                        result.add(instance as T)
                    } while (c.moveToNext())
                    c.close()
                    callback(result, null)
                }
            } catch (e: SQLiteException) {
                callback(null, e.message.toString())
            }
        }

        /**
         * [Comfort function]
         * Updates one or more rows from a table in the local SQLite database
         *
         * @param tableName       One or more table names
         * @param data            Object of a specified model, that contains the new values of data to be changed
         * @param condition       Object of type TPSQLiteCondition, which specifies the rows to be updated from the table
         * @param selectionArgs   Optional array of strings, which contains the binding arguments for the condition (note the order!)
         * @param callback        Callback function via which an error message can be returned
         *
         * @see TPSQLiteEntity
         * @see TPSQLiteCondition
         */
        inline fun <reified T: TPSQLiteEntity> update(
            tableName: String,
            data: T,
            condition: TPSQLiteCondition,
            selectionArgs: Array<String>? = null,
            callback: (error: String?) -> Unit
        ) {
            val db = dbHelper.writableDatabase
            val members = T::class.declaredMembers
            val contentValues = ContentValues()

            members.forEach { member ->
                when (member.returnType.toString()) {
                    "kotlin.Boolean" -> contentValues.put(member.name, member.call(data) as Boolean)
                    "kotlin.Byte" -> contentValues.put(member.name, member.call(data) as Byte)
                    "kotlin.ByteArray" -> contentValues.put(member.name, member.call(data) as ByteArray)
                    "kotlin.Double" -> contentValues.put(member.name, member.call(data) as Double)
                    "kotlin.Float" -> contentValues.put(member.name, member.call(data) as Float)
                    "kotlin.Int" -> contentValues.put(member.name, member.call(data) as Int)
                    "kotlin.Long" -> contentValues.put(member.name, member.call(data) as Long)
                    "kotlin.Short" -> contentValues.put(member.name, member.call(data) as Short)
                    "kotlin.String" -> contentValues.put(member.name, member.call(data) as String)
                }
            }
            try {
                db.update(tableName, contentValues, condition.toString(), selectionArgs)
                callback(null)
            } catch (e: SQLiteException) {
                callback(e.message.toString())
            }
        }

        /**
         * [Comfort function]
         * Removes one or more rows from a table in the local SQLite database
         *
         * @param tableName       Name of the table
         * @param condition       Optional object of type TPSQLiteCondition, which specifies the rows to be removed from the table
         * @param selectionArgs   Optional array of strings, which contains the binding arguments for the condition (note the order!)
         * @param callback        Callback function via which an error message can be returned
         *
         * @see TPSQLiteCondition
         */
        fun delete(
            tableName: String,
            condition: TPSQLiteCondition? = null,
            selectionArgs: Array<String>? = null,
            callback: (error: String?) -> Unit
        ) {
            val db = dbHelper.writableDatabase

            try {
                db.delete(tableName, condition.toString(), selectionArgs)
                callback(null)
            } catch (e: SQLiteException) {
                callback(e.message.toString())
            }
        }
    }
}
