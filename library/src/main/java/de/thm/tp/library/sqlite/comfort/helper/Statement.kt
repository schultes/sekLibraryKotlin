package de.thm.tp.library.sqlite.comfort.helper

internal abstract class Statement {
    private var logic: String = ""

    constructor(logic: String) {
        this.logic = logic
    }

    open fun getStatement(): String = logic
    open fun getOperator(): String = ""
}
