package de.thm.tp.library.sqlite.comfort

import de.thm.tp.library.sqlite.comfort.queryBuilder.TPSQLiteOperator

/**
 * Represents a condition. It requires 1 - 3 expressions.
 *
 * @param left            Operand on the left side
 * @param center          Operator or entire condition
 * @param right           Operand on the right side
 */
class TPSQLiteCondition {
    private var left: String = ""
    private var center: String = ""
    private var right: String = ""

    constructor(left: String, center: String, right: String) {
        this.left = left
        this.center = center
        this.right = right
    }

    constructor(left: String, right: String) {
        this.left = left
        this.right = right
    }

    constructor(center: String) {
        this.center = center
    }

    /**
     * Returns a string representation of the condition
     *
     * @return String
     */
    override fun toString(): String = "${this.left} ${this.center} ${this.right}"

    /**
     * Checks if the value of the condition on which the function was executed is equal to the given value
     *
     * @param value           String to be compared with the value of the given condition (we recommend to set '?' as value)
     *
     * @return Object of type TPSQLiteCondition
     */
    fun eq(value: String): TPSQLiteCondition {
        return TPSQLiteOperator.eq(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is equal to the given value
     *
     * @param value           Integer to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun eq(value: Int): TPSQLiteCondition {
        return TPSQLiteOperator.eq(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is equal to the given value
     *
     * @param value           Double to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun eq(value: Double): TPSQLiteCondition {
        return TPSQLiteOperator.eq(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is not equal to the given value
     *
     * @param value           String to be compared with the value of the given condition (we recommend to set '?' as value)
     *
     * @return Object of type TPSQLiteCondition
     */
    fun neq(value: String): TPSQLiteCondition {
        return TPSQLiteOperator.neq(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is not equal to the given value
     *
     * @param value           Integer to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun neq(value: Int): TPSQLiteCondition {
        return TPSQLiteOperator.neq(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is not equal to the given value
     *
     * @param value           Double to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun neq(value: Double): TPSQLiteCondition {
        return TPSQLiteOperator.neq(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is greater than the given value
     *
     * @param value           String to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun greater(value: String): TPSQLiteCondition {
        return TPSQLiteOperator.greater(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is greater than or equal to the given value
     *
     * @param value           String to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun greaterEq(value: String): TPSQLiteCondition {
        return TPSQLiteOperator.greaterEq(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is less than the given value
     *
     * @param value           String to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun less(value: String): TPSQLiteCondition {
        return TPSQLiteOperator.less(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is less than or equal to the given value
     *
     * @param value           String to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun lessEq(value: String): TPSQLiteCondition {
        return TPSQLiteOperator.lessEq(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is greater than the given value
     *
     * @param value           Integer to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun greater(value: Int): TPSQLiteCondition {
        return TPSQLiteOperator.greater(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is greater than or equal to the given value
     *
     * @param value           Integer to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun greaterEq(value: Int): TPSQLiteCondition {
        return TPSQLiteOperator.greaterEq(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is less than to the given value
     *
     * @param value           Integer to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun less(value: Int): TPSQLiteCondition {
        return TPSQLiteOperator.less(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is less than or equal to the given value
     *
     * @param value           Integer to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun lessEq(value: Int): TPSQLiteCondition {
        return TPSQLiteOperator.lessEq(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is greater than the given value
     *
     * @param value           Double to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun greater(value: Double): TPSQLiteCondition {
        return TPSQLiteOperator.greater(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is greater than or equal to the given value
     *
     * @param value           Double to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun greaterEq(value: Double): TPSQLiteCondition {
        return TPSQLiteOperator.greaterEq(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is less than the given value
     *
     * @param value           Double to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun less(value: Double): TPSQLiteCondition {
        return TPSQLiteOperator.less(this.toString(), value)
    }

    /**
     * Checks if the value of the condition on which the function was executed is less than or equal to the given value
     *
     * @param value           Double to be compared with the value of the given condition
     *
     * @return Object of type TPSQLiteCondition
     */
    fun lessEq(value: Double): TPSQLiteCondition {
        return TPSQLiteOperator.lessEq(this.toString(), value)
    }

    /**
     * Links the condition on which the function was executed and the given condition with a logical AND
     *
     * @param value           Object of type TPSQLiteCondition

     * @return Object of type TPSQLiteCondition
     */
    fun and(value: TPSQLiteCondition): TPSQLiteCondition {
        return TPSQLiteCondition(this.toString(), " AND ", value.toString())
    }

    /**
     * Links the condition on which the function was executed and the given condition with a logical OR
     *
     * @param value           Object of type TPSQLiteCondition

     * @return Object of type TPSQLiteCondition
     */
    fun or(value: TPSQLiteCondition): TPSQLiteCondition {
        return TPSQLiteCondition(this.toString(), " OR ", value.toString())
    }
}
