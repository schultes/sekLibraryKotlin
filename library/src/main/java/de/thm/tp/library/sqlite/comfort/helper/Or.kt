package de.thm.tp.library.sqlite.comfort.helper

internal class Or : Statement {
    constructor(logic: String) : super(logic)
    override fun getOperator(): String = "OR"
}
