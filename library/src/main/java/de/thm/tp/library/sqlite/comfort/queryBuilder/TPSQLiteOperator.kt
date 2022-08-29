package de.thm.tp.library.sqlite.comfort.queryBuilder

import de.thm.tp.library.sqlite.comfort.TPSQLiteCondition

/**
 * Represents the structure of a SQLite Operator
 */
class TPSQLiteOperator {
    companion object {
        /**
         * Takes a column and checks if its value is equal to the given value
         *
         * @param col             Name of the column
         * @param value           String to be compared with the value of the given column (if useRawValue isn't explicitly set true, we recommend to set '?' as value)
         * @param useRawValue     Boolean that defines whether to use the given value directly in the condition, otherwise '?' is used
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun eq(col: String, value: String, useRawValue: Boolean = false): TPSQLiteCondition {
            return TPSQLiteCondition(col, "=", if (useRawValue) value else "?")
        }

        /**
         * Takes a column and checks if its value is not equal to the given value
         *
         * @param col             Name of the column
         * @param value           String to be compared with the value of the given column (if useRawValue isn't explicitly set true, we recommend to set '?' as value)
         * @param useRawValue     Boolean that defines whether to use the given value directly in the condition, otherwise '?' is used
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun neq(col: String, value: String, useRawValue: Boolean = false): TPSQLiteCondition {
            return TPSQLiteCondition(col, "!=", if (useRawValue) value else "?")
        }

        /**
         * Takes a column and checks if its value is equal to the given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the value of the given column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun eq(col: String, value: Int): TPSQLiteCondition {
            return TPSQLiteCondition(col, "=", value.toString())
        }

        /**
         * Takes a column and checks if its value is not equal to the given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the value of the given column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun neq(col: String, value: Int): TPSQLiteCondition {
            return TPSQLiteCondition(col, "!=", value.toString())
        }

        /**
         * Takes a column and checks if its value is equal to the given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the value of the given column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun eq(col: String, value: Double): TPSQLiteCondition {
            return TPSQLiteCondition(col, "=", value.toString())
        }

        /**
         * Takes a column and checks if its value is not equal to the given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the value of the given column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun neq(col: String, value: Double): TPSQLiteCondition {
            return TPSQLiteCondition(col, "!=", value.toString())
        }

        /**
         * Takes two columns and checks if the value of the first is greater than the value of the second
         *
         * @param col             Name of the first column
         * @param col2            Name of the second column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun greater(col: String, col2: String): TPSQLiteCondition {
            return TPSQLiteCondition(col, ">", col2)
        }

        /**
         * Takes two columns and checks if the value of the first is greater than or equal to the value of the second
         *
         * @param col             Name of the first column
         * @param col2            Name of the second column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun greaterEq(col: String, col2: String): TPSQLiteCondition {
            return TPSQLiteCondition(col, ">=", col2)
        }

        /**
         * Takes two columns and checks if the value of the first is less than the value of the second
         *
         * @param col             Name of the first column
         * @param col2            Name of the second column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun less(col: String, col2: String): TPSQLiteCondition {
            return TPSQLiteCondition(col, "<", col2)
        }

        /**
         * Takes two columns and checks if the value of the first is less than or equal to the value of the second
         *
         * @param col             Name of the first column
         * @param col2            Name of the second column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun lessEq(col: String, col2: String): TPSQLiteCondition {
            return TPSQLiteCondition(col, "<=", col2)
        }

        /**
         * Takes a column and checks if its value is greater than the given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the value of the given column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun greater(col: String, value: Int): TPSQLiteCondition {
            return TPSQLiteCondition(col, ">", value.toString())
        }

        /**
         * Takes a column and checks if its value is greater than or equal to the given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the value of the given column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun greaterEq(col: String, value: Int): TPSQLiteCondition {
            return TPSQLiteCondition(col, ">=", value.toString())
        }

        /**
         * Takes a column and checks if its value is less than the given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the value of the given column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun less(col: String, value: Int): TPSQLiteCondition {
            return TPSQLiteCondition(col, "<", value.toString())
        }

        /**
         * Takes a column and checks if its value is less than or equal to the given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the value of the given column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun lessEq(col: String, value: Int): TPSQLiteCondition {
            return TPSQLiteCondition(col, "<=", value.toString())
        }

        /**
         * Takes a column and checks if its value is greater than the given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the value of the given column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun greater(col: String, value: Double): TPSQLiteCondition {
            return TPSQLiteCondition(col, ">", value.toString())
        }

        /**
         * Takes a column and checks if its value is greater than or equal to the given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the value of the given column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun greaterEq(col: String, value: Double): TPSQLiteCondition {
            return TPSQLiteCondition(col, ">=", value.toString())
        }

        /**
         * Takes a column and checks if its value is less than the given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the value of the given column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun less(col: String, value: Double): TPSQLiteCondition {
            return TPSQLiteCondition(col, "<", value.toString())
        }

        /**
         * Takes a column and checks if its value is less than or equal to the given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the value of the given column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun lessEq(col: String, value: Double): TPSQLiteCondition {
            return TPSQLiteCondition(col, "<=", value.toString())
        }

        /**
         * Takes a column and checks if its value is like the given value
         *
         * @param col             Name of the column
         * @param value           String to be compared with the value of the given column (if useRawValue isn't explicitly set true, we recommend to set '?' as value)
         * @param useRawValue     Boolean that defines whether to use the given value directly in the condition, otherwise '?' is used
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun like(col: String, value: String, useRawValue: Boolean = false): TPSQLiteCondition {
            return TPSQLiteCondition(
                col,
                "LIKE",
                if (useRawValue) "'${value.replace("'", "''")}'" else "?"
            )
        }

        /**
         * Takes a column and checks if its value is not like the given value
         *
         * @param col             Name of the column
         * @param value           String to be compared with the value of the given column (if useRawValue isn't explicitly set true, we recommend to set '?' as value)
         * @param useRawValue     Boolean that defines whether to use the given value directly in the condition, otherwise '?' is used
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun notLike(col: String, value: String, useRawValue: Boolean = false): TPSQLiteCondition {
            return TPSQLiteCondition(
                col,
                "NOT LIKE",
                if (useRawValue) "'${value.replace("'", "''")}'" else "?"
            )
        }

        /**
         * Takes a column and checks if its value is between the first given value and the second given value
         *
         * @param col             Name of the column
         * @param value1          Lower limit of the range to be compared with the value of the given column (if useRawValue isn't explicitly set true, we recommend to set '?' as value)
         * @param value2          Upper limit of the range to be compared with the value of the given column (if useRawValue isn't explicitly set true, we recommend to set '?' as value)
         * @param useRawValue     Boolean that defines whether to use the given values directly in the condition, otherwise '?' is used
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun between(
            col: String,
            value1: String,
            value2: String,
            useRawValue: Boolean = false
        ): TPSQLiteCondition {
            if (!useRawValue) {
                return TPSQLiteCondition("$col BETWEEN ", "?", " AND ?")
            }
            return TPSQLiteCondition("$col BETWEEN ", value1, " AND $value2")
        }

        /**
         * Takes a column and checks if its value is between the first given value and the second given value
         *
         * @param col             Name of the column
         * @param value1          Lower limit of the range to be compared with the value of the given column
         * @param value2          Upper limit of the range to be compared with the value of the given column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun between(col: String, value1: Int, value2: Int): TPSQLiteCondition {
            return TPSQLiteCondition("$col BETWEEN ", "$value1", " AND $value2")
        }

        /**
         * Takes a column and checks if its value is between the first given value and the second given value
         *
         * @param col             Name of the column
         * @param value1          Lower limit of the range to be compared with the value of the given column
         * @param value2          Upper limit of the range to be compared with the value of the given column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun between(col: String, value1: Double, value2: Double): TPSQLiteCondition {
            return TPSQLiteCondition("$col BETWEEN ", "$value1", " AND $value2")
        }

        /**
         * Takes a statement and checks if its not null
         *
         * @param stmt            String representation of the statement
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun isNotNull(stmt: String): TPSQLiteCondition {
            return TPSQLiteCondition(stmt, "IS NOT NULL")
        }

        /**
         * Takes a statement and checks if its null
         *
         * @param stmt            String representation of the statement
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun isNull(stmt: String): TPSQLiteCondition {
            return TPSQLiteCondition(stmt, "IS NULL")
        }

        /**
         * Takes a statement and checks if its a part of the given values
         *
         * @param stmt            String representation of the statement
         * @param values          String representation of the values
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun inList(stmt: String, values: String): TPSQLiteCondition {
            return TPSQLiteCondition(stmt, "IN", "($values)")
        }

        /**
         * Takes a statement and checks if its a part of the given values
         *
         * @param stmt            String representation of the statement
         * @param valuesSize      Number of values to be compared ('?' will be used)
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun inList(stmt: String, valuesSize: Int): TPSQLiteCondition {
            var valEsp = "?,".repeat(valuesSize)
            valEsp = valEsp.substring(0, valEsp.length - 1)
            return TPSQLiteCondition(stmt, "IN", "($valEsp)")
        }

        /**
         * Takes a statement and checks if its not a part of the given values
         *
         * @param stmt            String representation of the statement
         * @param values          String representation of the values
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun notInList(stmt: String, values: String): TPSQLiteCondition {
            return TPSQLiteCondition(stmt, "NOT IN", "($values)")
        }

        /**
         * Takes a statement and checks if its not a part of the given values
         *
         * @param stmt            String representation of the statement
         * @param valuesSize      Number of values to be compared ('?' will be used)
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun notInList(stmt: String, valuesSize: Int): TPSQLiteCondition {
            var valEsp = "?,".repeat(valuesSize)
            valEsp = valEsp.substring(0, valEsp.length - 1)
            return TPSQLiteCondition(stmt, "NOT IN", "($valEsp)")
        }

        /**
         * Encloses the specified condition in parentheses
         *
         * @param value           Object of type TPSQLiteCondition
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun sub(value: TPSQLiteCondition): TPSQLiteCondition {
            return TPSQLiteCondition("( ", value.toString(), " )")
        }

        /**
         * Encloses the specified condition in parentheses
         *
         * @param value           String representation of the condition
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun sub(value: String): TPSQLiteCondition {
            return TPSQLiteCondition("( ", value, " )")
        }
    }
}