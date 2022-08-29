package de.thm.tp.library.sqlite.comfort.helper

internal class And : Statement {
    constructor(logic: String) : super(logic)
    override fun getOperator(): String = "AND"
}
