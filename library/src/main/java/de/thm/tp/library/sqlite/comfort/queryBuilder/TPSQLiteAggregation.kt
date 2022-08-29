package de.thm.tp.library.sqlite.comfort.queryBuilder

import de.thm.tp.library.sqlite.comfort.TPSQLiteCondition

/**
 * Represents the structure of a SQLite aggregation
 */
class TPSQLiteAggregation {
    companion object {
        /**
         * Represents the SQLite SUM function
         *
         * @param col             Name of the column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun sum(col: String): TPSQLiteCondition {
            return TPSQLiteCondition("SUM( ", col, " ) ")
        }

        /**
         * Represents the SQLite COUNT function
         *
         * @param col             Name of the column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun count(col: String): TPSQLiteCondition {
            return TPSQLiteCondition("COUNT( ", col, " ) ")
        }

        /**
         * Represents the SQLite AVG function
         *
         * @param col             Name of the column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun avg(col: String): TPSQLiteCondition {
            return TPSQLiteCondition("AVG( ", col, " ) ")
        }

        /**
         * Represents the SQLite MIN function
         *
         * @param col             Name of the column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun min(col: String): TPSQLiteCondition {
            return TPSQLiteCondition("MIN( ", col, " ) ")
        }

        /**
         * Represents the SQLite MAX function
         *
         * @param col             Name of the column
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun max(col: String): TPSQLiteCondition {
            return TPSQLiteCondition("MAX( ", col, " ) ")
        }

        /**
         * Applies the SQLite SUM function to a given column and checks if its sum is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the SUM function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun sum(col: String, value: Int): TPSQLiteCondition {
            return sumEq(col, value)
        }

        /**
         * Applies the SQLite SUM function to a given column and checks if its sum is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the SUM function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun sumEq(col: String, value: Int): TPSQLiteCondition {
            return agg("SUM", col, "=", value)
        }

        /**
         * Applies the SQLite SUM function to a given column and checks if its sum is greater than a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the SUM function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun sumGt(col: String, value: Int): TPSQLiteCondition {
            return agg("SUM", col, ">", value)
        }

        /**
         * Applies the SQLite SUM function to a given column and checks if its sum is less than a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the SUM function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun sumLt(col: String, value: Int): TPSQLiteCondition {
            return agg("SUM", col, "<", value)
        }

        /**
         * Applies the SQLite SUM function to a given column and checks if its sum is greater than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the SUM function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun sumGe(col: String, value: Int): TPSQLiteCondition {
            return agg("SUM", col, ">=", value)
        }

        /**
         * Applies the SQLite SUM function to a given column and checks if its sum is less than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the SUM function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun sumLe(col: String, value: Int): TPSQLiteCondition {
            return agg("SUM", col, "<=", value)
        }

        /**
         * Applies the SQLite COUNT function to a given column and checks if its count is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the COUNT function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun count(col: String, value: Int): TPSQLiteCondition {
            return countEq(col, value)
        }

        /**
         * Applies the SQLite COUNT function to a given column and checks if its count is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the COUNT function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun countEq(col: String, value: Int): TPSQLiteCondition {
            return agg("COUNT", col, "=", value)
        }

        /**
         * Applies the SQLite COUNT function to a given column and checks if its count is greater than a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the COUNT function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun countGt(col: String, value: Int): TPSQLiteCondition {
            return agg("COUNT", col, ">", value)
        }

        /**
         * Applies the SQLite COUNT function to a given column and checks if its count is less than a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the COUNT function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun countLt(col: String, value: Int): TPSQLiteCondition {
            return agg("COUNT", col, "<", value)
        }

        /**
         * Applies the SQLite COUNT function to a given column and checks if its count is greater than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the COUNT function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun countGe(col: String, value: Int): TPSQLiteCondition {
            return agg("COUNT", col, ">=", value)
        }

        /**
         * Applies the SQLite COUNT function to a given column and checks if its count is less than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the COUNT function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun countLe(col: String, value: Int): TPSQLiteCondition {
            return agg("COUNT", col, "<=", value)
        }

        /**
         * Applies the SQLite AVG function to a given column and checks if its average is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the AVG function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun avg(col: String, value: Int): TPSQLiteCondition {
            return avgEq(col, value)
        }

        /**
         * Applies the SQLite AVG function to a given column and checks if its average is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the AVG function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun avgEq(col: String, value: Int): TPSQLiteCondition {
            return agg("AVG", col, "=", value)
        }

        /**
         * Applies the SQLite AVG function to a given column and checks if its average is greater than a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the AVG function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun avgGt(col: String, value: Int): TPSQLiteCondition {
            return agg("AVG", col, ">", value)
        }

        /**
         * Applies the SQLite AVG function to a given column and checks if its average is less than a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the AVG function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun avgLt(col: String, value: Int): TPSQLiteCondition {
            return agg("AVG", col, "<", value)
        }

        /**
         * Applies the SQLite AVG function to a given column and checks if its average is greater than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the AVG function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun avgGe(col: String, value: Int): TPSQLiteCondition {
            return agg("AVG", col, ">=", value)
        }

        /**
         * Applies the SQLite AVG function to a given column and checks if its average is less than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the AVG function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun avgLe(col: String, value: Int): TPSQLiteCondition {
            return agg("AVG", col, "<=", value)
        }

        /**
         * Applies the SQLite MIN function to a given column and checks if its minimum is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the MIN function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun min(col: String, value: Int): TPSQLiteCondition {
            return minEq(col, value)
        }

        /**
         * Applies the SQLite MIN function to a given column and checks if its minimum is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the MIN function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun minEq(col: String, value: Int): TPSQLiteCondition {
            return agg("MIN", col, "=", value)
        }

        /**
         * Applies the SQLite MIN function to a given column and checks if its minimum is greater than a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the MIN function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun minGt(col: String, value: Int): TPSQLiteCondition {
            return agg("MIN", col, ">", value)
        }

        /**
         * Applies the SQLite MIN function to a given column and checks if its minimum is less than a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the MIN function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun minLt(col: String, value: Int): TPSQLiteCondition {
            return agg("MIN", col, "<", value)
        }

        /**
         * Applies the SQLite MIN function to a given column and checks if its minimum is greater than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the MIN function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun minGe(col: String, value: Int): TPSQLiteCondition {
            return agg("MIN", col, ">=", value)
        }

        /**
         * Applies the SQLite MIN function to a given column and checks if its minimum is less than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the MIN function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun minLe(col: String, value: Int): TPSQLiteCondition {
            return agg("MIN", col, "<=", value)
        }

        /**
         * Applies the SQLite MAX function to a given column and checks if its maximum is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the MAX function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun max(col: String, value: Int): TPSQLiteCondition {
            return maxEq(col, value)
        }

        /**
         * Applies the SQLite MAX function to a given column and checks if its maximum is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the MAX function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun maxEq(col: String, value: Int): TPSQLiteCondition {
            return agg("MAX", col, "=", value)
        }

        /**
         * Applies the SQLite MAX function to a given column and checks if its maximum is greater than a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the MAX function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun maxGt(col: String, value: Int): TPSQLiteCondition {
            return agg("MAX", col, ">", value)
        }

        /**
         * Applies the SQLite MAX function to a given column and checks if its maximum is less than a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the MAX function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun maxLt(col: String, value: Int): TPSQLiteCondition {
            return agg("MAX", col, "<", value)
        }

        /**
         * Applies the SQLite MAX function to a given column and checks if its maximum is greater than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the MAX function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun maxGe(col: String, value: Int): TPSQLiteCondition {
            return agg("MAX", col, ">=", value)
        }

        /**
         * Applies the SQLite MAX function to a given column and checks if its maximum is less than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Integer to be compared with the result of the MAX function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun maxLe(col: String, value: Int): TPSQLiteCondition {
            return agg("MAX", col, "<=", value)
        }

        private fun agg(funcName: String, col: String, op: String, value: Int): TPSQLiteCondition {
            return TPSQLiteCondition("$funcName(", col, ") $op $value")
        }

        /**
         * Applies the SQLite SUM function to a given column and checks if its sum is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the SUM function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun sumEq(col: String, value: Double): TPSQLiteCondition {
            return agg("SUM", col, "=", value)
        }

        /**
         * Applies the SQLite SUM function to a given column and checks if its sum is greater than a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the SUM function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun sumGt(col: String, value: Double): TPSQLiteCondition {
            return agg("SUM", col, ">", value)
        }

        /**
         * Applies the SQLite SUM function to a given column and checks if its sum is less than a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the SUM function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun sumLt(col: String, value: Double): TPSQLiteCondition {
            return agg("SUM", col, "<", value)
        }

        /**
         * Applies the SQLite SUM function to a given column and checks if its sum is greater than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the SUM function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun sumGe(col: String, value: Double): TPSQLiteCondition {
            return agg("SUM", col, ">=", value)
        }

        /**
         * Applies the SQLite SUM function to a given column and checks if its sum is less than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the SUM function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun sumLe(col: String, value: Double): TPSQLiteCondition {
            return agg("SUM", col, "<=", value)
        }

        /**
         * Applies the SQLite COUNT function to a given column and checks if its count is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the COUNT function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun count(col: String, value: Double): TPSQLiteCondition {
            return countEq(col, value)
        }

        /**
         * Applies the SQLite COUNT function to a given column and checks if its count is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the COUNT function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun countEq(col: String, value: Double): TPSQLiteCondition {
            return agg("COUNT", col, "=", value)
        }

        /**
         * Applies the SQLite COUNT function to a given column and checks if its count is greater than a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the COUNT function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun countGt(col: String, value: Double): TPSQLiteCondition {
            return agg("COUNT", col, ">", value)
        }

        /**
         * Applies the SQLite COUNT function to a given column and checks if its count is less than a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the COUNT function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun countLt(col: String, value: Double): TPSQLiteCondition {
            return agg("COUNT", col, "<", value)
        }

        /**
         * Applies the SQLite COUNT function to a given column and checks if its count is greater than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the COUNT function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun countGe(col: String, value: Double): TPSQLiteCondition {
            return agg("COUNT", col, ">=", value)
        }

        /**
         * Applies the SQLite COUNT function to a given column and checks if its count is less than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the COUNT function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun countLe(col: String, value: Double): TPSQLiteCondition {
            return agg("COUNT", col, "<=", value)
        }

        /**
         * Applies the SQLite AVG function to a given column and checks if its average is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the AVG function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun avg(col: String, value: Double): TPSQLiteCondition {
            return avgEq(col, value)
        }

        /**
         * Applies the SQLite AVG function to a given column and checks if its average is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the AVG function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun avgEq(col: String, value: Double): TPSQLiteCondition {
            return agg("AVG", col, "=", value)
        }

        /**
         * Applies the SQLite AVG function to a given column and checks if its average is greater than a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the AVG function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun avgGt(col: String, value: Double): TPSQLiteCondition {
            return agg("AVG", col, ">", value)
        }

        /**
         * Applies the SQLite AVG function to a given column and checks if its average is less than a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the AVG function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun avgLt(col: String, value: Double): TPSQLiteCondition {
            return agg("AVG", col, "<", value)
        }

        /**
         * Applies the SQLite AVG function to a given column and checks if its average is greater than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the AVG function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun avgGe(col: String, value: Double): TPSQLiteCondition {
            return agg("AVG", col, ">=", value)
        }

        /**
         * Applies the SQLite AVG function to a given column and checks if its average is less than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the AVG function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun avgLe(col: String, value: Double): TPSQLiteCondition {
            return agg("AVG", col, "<=", value)
        }

        /**
         * Applies the SQLite MIN function to a given column and checks if its minimum is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the MIN function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun min(col: String, value: Double): TPSQLiteCondition {
            return minEq(col, value)
        }

        /**
         * Applies the SQLite MIN function to a given column and checks if its minimum is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the MIN function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun minEq(col: String, value: Double): TPSQLiteCondition {
            return agg("MIN", col, "=", value)
        }

        /**
         * Applies the SQLite MIN function to a given column and checks if its minimum is greater than a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the MIN function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun minGt(col: String, value: Double): TPSQLiteCondition {
            return agg("MIN", col, ">", value)
        }

        /**
         * Applies the SQLite MIN function to a given column and checks if its minimum is less than a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the MIN function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun minLt(col: String, value: Double): TPSQLiteCondition {
            return agg("MIN", col, "<", value)
        }

        /**
         * Applies the SQLite MIN function to a given column and checks if its minimum is greater than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the MIN function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun minGe(col: String, value: Double): TPSQLiteCondition {
            return agg("MIN", col, ">=", value)
        }

        /**
         * Applies the SQLite MIN function to a given column and checks if its minimum is less than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the MIN function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun minLe(col: String, value: Double): TPSQLiteCondition {
            return agg("MIN", col, "<=", value)
        }

        /**
         * Applies the SQLite MAX function to a given column and checks if its maximum is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the MAX function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun max(col: String, value: Double): TPSQLiteCondition {
            return maxEq(col, value)
        }

        /**
         * Applies the SQLite MAX function to a given column and checks if its maximum is equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the MAX function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun maxEq(col: String, value: Double): TPSQLiteCondition {
            return agg("MAX", col, "=", value)
        }

        /**
         * Applies the SQLite MAX function to a given column and checks if its maximum is greater than a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the MAX function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun maxGt(col: String, value: Double): TPSQLiteCondition {
            return agg("MAX", col, ">", value)
        }

        /**
         * Applies the SQLite MAX function to a given column and checks if its maximum is less than a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the MAX function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun maxLt(col: String, value: Double): TPSQLiteCondition {
            return agg("MAX", col, "<", value)
        }

        /**
         * Applies the SQLite MAX function to a given column and checks if its maximum is greater than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the MAX function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun maxGe(col: String, value: Double): TPSQLiteCondition {
            return agg("MAX", col, ">=", value)
        }

        /**
         * Applies the SQLite MAX function to a given column and checks if its maximum is less than or equal to a given value
         *
         * @param col             Name of the column
         * @param value           Double to be compared with the result of the MAX function
         *
         * @return Object of type TPSQLiteCondition
         *
         * @see TPSQLiteCondition
         */
        fun maxLe(col: String, value: Double): TPSQLiteCondition {
            return agg("MAX", col, "<=", value)
        }

        private fun agg(
            funcName: String,
            col: String,
            op: String,
            value: Double
        ): TPSQLiteCondition {
            return TPSQLiteCondition("$funcName(", col, ") $op $value")
        }
    }
}