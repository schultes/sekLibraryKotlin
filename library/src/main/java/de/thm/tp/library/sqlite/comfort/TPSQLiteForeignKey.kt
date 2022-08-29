package de.thm.tp.library.sqlite.comfort

/**
 * Represents the structure of a SQLite foreign key constraint and can be used when creating a table in the database
 *
 * @param col             String that represents a column in a table
 */
class TPSQLiteForeignKey(private val col: String) {
    private var referenceTable = ""
    private var referenceCol = ""
    private var onUpdateAction = ""
    private var onDeleteAction = ""

    fun getForeignKeyString(): String {
        return compileForeignKeys()
    }

    /**
     * References a table and the associated column
     *
     * @param table           Name of the table to be referenced
     * @param col             Name of the column of the referenced table
     */
    fun reference(table: String, col: String): TPSQLiteForeignKey {
        referenceTable = table
        referenceCol = col
        return this
    }

    /**
     *  Specifies how the foreign key constraint behaves whenever the parent key is updated
     *
     * @param action          Object of type TPSQLiteForeignKeyConstraintAction, which defines the action the be performed
     *
     * @see TPSQLiteForeignKeyConstraintAction
     */
    fun onUpdate(action: TPSQLiteForeignKeyConstraintAction): TPSQLiteForeignKey {
        onUpdateAction = action.value
        return this
    }

    /**
     *  Specifies how the foreign key constraint behaves whenever the parent key is deleted
     *
     * @param action          Object of type TPSQLiteForeignKeyConstraintAction, which defines the action the be performed
     *
     * @see TPSQLiteForeignKeyConstraintAction
     */
    fun onDelete(action: TPSQLiteForeignKeyConstraintAction): TPSQLiteForeignKey {
        onDeleteAction = action.value
        return this
    }

    private fun compileForeignKeys(): String {
        var result = "FOREIGN KEY ($col) REFERENCES ${referenceTable}(${referenceCol})"
        if (!onUpdateAction.isNullOrEmpty()) result += " ON UPDATE ${onUpdateAction}"
        if (!onDeleteAction.isNullOrEmpty()) result += " ON DELETE ${onDeleteAction}"
        return result
    }
}