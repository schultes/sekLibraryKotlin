package de.thm.tp.library.sqlite.comfort.helper

/**
 * Holds the column for the ORDER BY clause
 */
class TPSQLiteSort private constructor(var col: String, var direction: String = "ASC") {

    companion object {
        /**
         * Holds the column for the ORDER BY clause in ascending order
         *
         * @param col             Name of the column
         *
         * @return TPSQLiteSort
         */
        fun asc(col: String): TPSQLiteSort = TPSQLiteSort(col)

        /**
         * Holds the column for the ORDER BY clause in descending order
         *
         * @param col             Name of the column
         *
         * @return TPSQLiteSort
         */
        fun desc(col: String): TPSQLiteSort = TPSQLiteSort(col, "DESC")
    }
}
