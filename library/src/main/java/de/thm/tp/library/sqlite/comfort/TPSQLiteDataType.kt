package de.thm.tp.library.sqlite.comfort

/**
 * Enum that represents a SQLite data type.
 * Can be one of the following:
 * - Null, Integer, Real, Text, Blob
 */
enum class TPSQLiteDataType(val value: String) {
    Null("NULL"),
    Integer("INTEGER"),
    Real("REAL"),
    Text("TEXT"),
    Blob("BLOB")
}