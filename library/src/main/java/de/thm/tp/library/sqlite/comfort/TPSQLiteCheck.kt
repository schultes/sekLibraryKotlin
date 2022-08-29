package de.thm.tp.library.sqlite.comfort

import de.thm.tp.library.sqlite.comfort.helper.And
import de.thm.tp.library.sqlite.comfort.helper.Statement

/**
 * Represents the structure of a SQLite check constraint and can be used when creating a table in the database
 */
class TPSQLiteCheck {
    private var checkConditions = arrayListOf<Statement>()

    fun getCheckString(): String {
        var result = ""

        val check = compileCheck()

        if (check.isNotEmpty()) result += "CHECK $check"
        return result
    }

    /**
     * Contains the condition for the SQLite check constraint
     *
     * @param condition       Object of TPSQLiteCondition
     *
     * @see TPSQLiteCondition
     */
    fun check(condition: TPSQLiteCondition): TPSQLiteCheck {
        checkConditions.add(And(condition.toString()))
        return this
    }

    /**
     * Contains the conditions for the SQLite check constraint
     *
     * @param conditions      Objects of TPSQLiteCondition
     *
     * @see TPSQLiteCondition
     */
    fun check(vararg conditions: TPSQLiteCondition): TPSQLiteCheck {
        conditions.forEach {
            checkConditions.add(And(it.toString()))
        }
        return this
    }

    /**
     * Contains the condition for the SQLite check constraint
     *
     * @param condition      String that represents the condition
     */
    fun check(condition: String): TPSQLiteCheck {
        checkConditions.add(And(condition))
        return this
    }

    /**
     * Contains the conditions for the SQLite check constraint
     *
     * @param conditions      Strings that represents the condition
     */
    fun check(vararg conditions: String): TPSQLiteCheck {
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
        return result
    }
}