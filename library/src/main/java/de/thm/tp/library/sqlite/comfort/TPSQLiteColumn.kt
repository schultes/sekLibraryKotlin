package de.thm.tp.library.sqlite.comfort

import de.thm.tp.library.sqlite.comfort.helper.And
import de.thm.tp.library.sqlite.comfort.helper.Statement

/**
 * Represents the structure of a SQLite column and can be used when creating a table in the database
 *
 * @param name            Name of the column
 * @param dataType        Object of TPSQLiteDataType, which represents the data type of the column
 *
 * @see TPSQLiteDataType
 */
class TPSQLiteColumn(private val name: String, private val dataType: TPSQLiteDataType) {
    private var primaryKey = false
    private var notNull = false
    private var autoincrement = false
    private var unique = false
    private var checkConditions = arrayListOf<Statement>()

    fun getColumnString(): String {
        var result = ""

        val check = compileCheck()

        result += "$name ${dataType.value}"
        if (primaryKey) result += " PRIMARY KEY"
        if (notNull) result += " NOT NULL"
        if (unique) result += " UNIQUE"
        if (autoincrement) result += " AUTOINCREMENT"
        if (check.isNotEmpty()) result += " CHECK ($check)"
        return result
    }

    /**
     * Marks a column as a primary key
     */
    fun primaryKey(): TPSQLiteColumn {
        primaryKey = true
        return this
    }

    /**
     * Marks a column to be not null
     */
    fun notNull(): TPSQLiteColumn {
        notNull = true
        return this
    }

    /**
     * Marks a column to be auto incremented
     */
    fun autoincrement(): TPSQLiteColumn {
        autoincrement = true
        return this
    }

    /**
     * Marks a column to be unique
     */
    fun unique(): TPSQLiteColumn {
        unique = true
        return this
    }

    /**
     * Contains the condition for a SQLite check constraint
     *
     * @param condition       Object of TPSQLiteCondition
     *
     * @see TPSQLiteCondition
     */
    fun check(condition: TPSQLiteCondition): TPSQLiteColumn {
        checkConditions.add(And(condition.toString()))
        return this
    }

    /**
     * Contains the conditions for a SQLite check constraint
     *
     * @param conditions      Objects of TPSQLiteCondition
     *
     * @see TPSQLiteCondition
     */
    fun check(vararg conditions: TPSQLiteCondition): TPSQLiteColumn {
        conditions.forEach {
            checkConditions.add(And(it.toString()))
        }
        return this
    }

    /**
     * Contains the condition for a SQLite check constraint
     *
     * @param condition      String that represents the condition
     */
    fun check(condition: String): TPSQLiteColumn {
        checkConditions.add(And(condition))
        return this
    }

    /**
     * Contains the conditions for a SQLite check constraint
     *
     * @param conditions      Strings that represents the condition
     */
    fun check(vararg conditions: String): TPSQLiteColumn {
        conditions.forEach {
            checkConditions.add(And(it))
        }
        return this
    }

    private fun compileCheck(): String {
        var result = ""
        checkConditions.forEach {
            result += " ${it.getStatement()} ${it.getOperator()}"
        }
        if (result != "") {
            result = result.substring(0, result.length - 3).trim()
        }
        return  result
    }
}
