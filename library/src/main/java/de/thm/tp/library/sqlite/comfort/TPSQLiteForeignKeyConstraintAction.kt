package de.thm.tp.library.sqlite.comfort

/**
 * Enum that represents a SQLite foreign key constraint action.
 * Can be one of the following:
 * - SetNull, SetDefault, Restrict, NoAction, Cascade
 */
enum class TPSQLiteForeignKeyConstraintAction(val value: String) {
    SetNull("SET NULL"),
    SetDefault("SET DEFAULT"),
    Restrict("RESTRICT"),
    NoAction("NO ACTION"),
    Cascade("CASCADE")
}