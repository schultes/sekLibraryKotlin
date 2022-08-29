package de.thm.tp.library.sqlite.comfort

/**
 * Represents the structure of a SQLite Table and is used to create the specified table in the database
 *
 * @param name            Name of the table
 * @param columns         Array of objects of type TPSQLiteColumn, which defines the columns in the current table
 * @param check           Optional object of TPSQLiteCheck, which represents a SQLite Check constraint
 * @param foreignKeys     Optional array of objects of TPSQLiteForeignKey, which represents SQLite foreign key constraints
 *
 * @see TPSQLiteColumn
 * @see TPSQLiteCheck
 * @see TPSQLiteForeignKey
 */
class TPSQLiteTable(
    private val name: String,
    private val columns: Array<TPSQLiteColumn>,
    private val check: TPSQLiteCheck? = null,
    private val foreignKeys: Array<TPSQLiteForeignKey>? = null
) {
    fun createStatement(): String {
        var result = ""
        var tableColumns = ""

        columns.forEachIndexed { index, column ->
            if (index == columns.size - 1) tableColumns += column.getColumnString()
            else tableColumns += column.getColumnString() + ","
        }

        val checkString = check?.getCheckString()
        var foreignKeysString = ""

        foreignKeys?.forEach {
            foreignKeysString += it.getForeignKeyString()
        }

        result += "CREATE TABLE IF NOT EXISTS $name ($tableColumns"
        if (!checkString.isNullOrEmpty()) result += ",$checkString"
        if (!foreignKeysString.isNullOrEmpty()) result += ",$foreignKeysString"
        result += ");"

        return result
    }
}
