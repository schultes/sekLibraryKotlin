package de.thm.tp.library.sqlite.comfort.queryBuilder

import de.thm.tp.library.sqlite.comfort.TPSQLiteCondition
import de.thm.tp.library.sqlite.comfort.helper.*

/**
 * Represents the structure of a SQLite query
 */
class TPSQLiteQuery {
    private var currentTable: String = ""
    private val colsToSelect: LinkedHashMap<String, Array<String>> = LinkedHashMap()
    private var from = arrayListOf<String>()
    private var whereConditions = arrayListOf<Statement>()
    private val tablesToJoin: LinkedHashMap<String, ArrayList<Statement>> = LinkedHashMap()
    private var groupBys = arrayListOf<String>()
    private var havingConditions = arrayListOf<Statement>()
    private var orderBys = arrayListOf<TPSQLiteSort>()
    private var limitOffset: Array<Int>? = null

    fun toSQL(): String {
        var result = ""

        val _select = compileSelect()
        val _from = compileFrom()
        val _joins = compileJoins()
        val _where = compileWhere()
        val _groupBy = compileGroupBy()
        val _having = compileHaving()
        val _orderBy = compileOrderBy()
        val _limit = compileLimit()

        result += "SELECT $_select"
        result += " FROM $_from"
        if (_joins.isNotEmpty()) {
            result += " $_joins"
        }
        if (_where.isNotEmpty()) {
            result += " WHERE $_where"
        }
        if (_groupBy.isNotEmpty()) {
            result += " GROUP BY $_groupBy"
        }
        if (_groupBy.isNotEmpty() && _having.isNotEmpty()) {
            result += " HAVING $_having"
        }
        if (_orderBy.isNotEmpty()) {
            result += " ORDER BY $_orderBy"
        }
        if (_limit.isNotEmpty()) {
            result += " LIMIT $_limit"
        }

        return result
    }

    /**
     * Explicitly specifies the associated table for given columns (must be called before the select method).
     *
     * @param name            Name of the table
     */
    fun table(name: String): TPSQLiteQuery {
        currentTable = name
        return this
    }

    /**
     * Holds the relevant columns for the SELECT clause for the query
     *
     * @param cols            One or more column names
     */
    fun select(vararg cols: String): TPSQLiteQuery {
        colsToSelect[currentTable] = cols as Array<String>
        return this
    }

    /**
     * Holds the relevant table for the FROM clause of the query
     *
     * @param table           Name of the table
     */
    fun from(table: String): TPSQLiteQuery {
        from.add(table)
        return this
    }

    /**
     * Holds the relevant tables for the FROM clause of the query
     *
     * @param tables          One or more table names
     */
    fun from(vararg tables: String): TPSQLiteQuery {
        from.addAll(tables)
        return this
    }

    /**
     * Holds a subquery for the FROM clause of the query
     *
     * @param query           Object of TPSQLiteQuery, which represents the subquery
     *
     * @see TPSQLiteQuery
     */
    fun from(query: TPSQLiteQuery): TPSQLiteQuery {
        from.add("(${query.toSQL()})")
        return this
    }

    /**
     * Holds a join for the specified table (don't forget to call the join condition)
     *
     * @param table           Name of the table to be joined
     *
     * @see On
     * @see On
     * @see On
     * @see On
     * @see On
     * @see On
     */
    fun join(table: String): TPSQLiteQuery {
        tablesToJoin["JOIN-" + table] = ArrayList()
        return this
    }

    /**
     * Holds a left join for the specified table (don't forget to call the join condition)
     *
     * @param table           Name of the table to be joined
     *
     * @see On
     * @see On
     * @see On
     * @see On
     * @see On
     * @see On
     */
    fun leftJoin(table: String): TPSQLiteQuery {
        tablesToJoin["LEFT-JOIN-" + table] = ArrayList()
        return this
    }

    /**
     * Holds a left outer join for the specified table (don't forget to call the join condition)
     *
     * @param table           Name of the table to be joined
     *
     * @see On
     * @see On
     * @see On
     * @see On
     * @see On
     * @see On
     */
    fun leftOuterJoin(table: String): TPSQLiteQuery {
        tablesToJoin["LEFT-OUTER-JOIN-" + table] = ArrayList()
        return this
    }

    /**
     * Holds a right join for the specified table (don't forget to call the join condition)
     *
     * @param table           Name of the table to be joined
     *
     * @see On
     * @see On
     * @see On
     * @see On
     * @see On
     * @see On
     */
    fun rightJoin(table: String): TPSQLiteQuery {
        tablesToJoin["RIGHT-JOIN-" + table] = ArrayList()
        return this
    }

    /**
     * Holds a right outer join for the specified table (don't forget to call the join condition)
     *
     * @param table           Name of the table to be joined
     *
     * @see On
     * @see On
     * @see On
     * @see On
     * @see On
     * @see On
     */
    fun rightOuterJoin(table: String): TPSQLiteQuery {
        tablesToJoin["RIGHT-OUTER-JOIN-" + table] = ArrayList()
        return this
    }

    /**
     * Holds a inner join for the specified table (don't forget to call the join condition)
     *
     * @param table           Name of the table to be joined
     *
     * @see On
     * @see On
     * @see On
     * @see On
     * @see On
     * @see On
     */
    fun innerJoin(table: String): TPSQLiteQuery {
        tablesToJoin["INNER-JOIN-" + table] = ArrayList()
        return this
    }

    /**
     * Holds a cross join for the specified table (don't forget to call the join condition)
     *
     * @param table           Name of the table to be joined
     *
     * @see On
     * @see On
     * @see On
     * @see On
     * @see On
     * @see On
     */
    fun crossJoin(table: String): TPSQLiteQuery {
        tablesToJoin["CROSS-JOIN-" + table] = ArrayList()
        return this
    }

    /**
     * Holds the join condition for the previously defined join
     *
     * @param condition       Object of TPSQLiteCondition
     *
     * @see TPSQLiteCondition
     */
    fun on(condition: TPSQLiteCondition): TPSQLiteQuery {
        if (tablesToJoin.size > 0) {
            val tblKey = tablesToJoin.entries.last().key
            tablesToJoin[tblKey]?.add(And(condition.toString()))
        }
        return this
    }

    /**
     * Holds the join conditions for the previously defined join
     *
     * @param useOr           Boolean that defines whether the conditions are linked via an AND or an OR.
     * @param conditions      Objects of TPSQLiteCondition
     *
     * @see TPSQLiteCondition
     */
    fun on(useOr: Boolean = false, vararg conditions: TPSQLiteCondition): TPSQLiteQuery {
        if (tablesToJoin.size > 0) {
            val tblKey = tablesToJoin.entries.last().key
            if (useOr) {
                conditions.forEach {
                    tablesToJoin[tblKey]?.add(Or(it.toString()))
                }
            } else {
                conditions.forEach {
                    tablesToJoin[tblKey]?.add(And(it.toString()))
                }
            }

        }
        return this
    }

    /**
     * Holds the join conditions for the previously defined join
     *
     * @param conditions      Objects of TPSQLiteCondition
     *
     * @see TPSQLiteCondition
     */
    fun on(vararg conditions: TPSQLiteCondition): TPSQLiteQuery {
        if (tablesToJoin.size > 0) {
            val tblKey = tablesToJoin.entries.last().key
            conditions.forEach {
                tablesToJoin[tblKey]?.add(And(it.toString()))
            }
        }
        return this
    }

    /**
     * Holds the join condition for the previously defined join
     *
     * @param condition       String representation of the condition
     */
    fun on(condition: String): TPSQLiteQuery {
        if (tablesToJoin.size > 0) {
            val tblKey = tablesToJoin.entries.last().key
            tablesToJoin[tblKey]?.add(And(condition))
        }
        return this
    }

    /**
     * Holds the join conditions for the previously defined join
     *
     * @param useOr           Boolean that defines whether the conditions are linked via an AND or an OR.
     * @param conditions      One or more string representations of the condition
     */
    fun on(useOr: Boolean = false, vararg conditions: String): TPSQLiteQuery {
        if (tablesToJoin.size > 0) {
            val tblKey = tablesToJoin.entries.last().key
            if (useOr) {
                conditions.forEach {
                    tablesToJoin[tblKey]?.add(Or(it.toString()))
                }
            } else {
                conditions.forEach {
                    tablesToJoin[tblKey]?.add(And(it.toString()))
                }
            }
        }
        return this
    }

    /**
     * Holds the join conditions for the previously defined join
     *
     * @param conditions      One or more string representations of the condition
     */
    fun on(vararg conditions: String): TPSQLiteQuery {
        if (tablesToJoin.size > 0) {
            val tblKey = tablesToJoin.entries.last().key
            conditions.forEach {
                tablesToJoin[tblKey]?.add(And(it.toString()))
            }
        }
        return this
    }

    /**
     * Holds the condition for the WHERE clause of the query
     *
     * @param condition       Object of TPSQLiteCondition
     *
     * @see TPSQLiteCondition
     */
    fun condition(condition: TPSQLiteCondition): TPSQLiteQuery {
        whereConditions.add(And(condition.toString()))
        return this
    }

    /**
     * Holds the conditions for the WHERE clause of the query
     *
     * @param useOr           Boolean that defines whether the conditions are linked via an AND or an OR.
     * @param conditions      Objects of TPSQLiteCondition
     *
     * @see TPSQLiteCondition
     */
    fun condition(useOr: Boolean = false, vararg conditions: TPSQLiteCondition): TPSQLiteQuery {
        if (useOr) {
            conditions.forEach {
                whereConditions.add(Or(it.toString()))
            }
        } else {
            conditions.forEach {
                whereConditions.add(And(it.toString()))
            }
        }
        return this
    }

    /**
     * Holds the conditions for the WHERE clause of the query
     *
     * @param conditions      Objects of TPSQLiteCondition
     *
     * @see TPSQLiteCondition
     */
    fun condition(vararg conditions: TPSQLiteCondition): TPSQLiteQuery {
        conditions.forEach {
            whereConditions.add(And(it.toString()))
        }
        return this
    }

    /**
     * Holds the condition for the WHERE clause of the query
     *
     * @param condition       String representation of the condition
     */
    fun condition(condition: String): TPSQLiteQuery {
        whereConditions.add(And(condition))
        return this
    }

    /**
     * Holds the conditions for the WHERE clause of the query
     *
     * @param useOr           Boolean that defines whether the conditions are linked via an AND or an OR.
     * @param conditions      One or more string representations of the condition
     */
    fun condition(useOr: Boolean = false, vararg conditions: String): TPSQLiteQuery {
        if (useOr) {
            conditions.forEach {
                whereConditions.add(Or(it))
            }
        } else {
            conditions.forEach {
                whereConditions.add(And(it))
            }
        }
        return this
    }

    /**
     * Holds the conditions for the WHERE clause of the query
     *
     * @param conditions      One or more string representations of the condition
     */
    fun condition(vararg conditions: String): TPSQLiteQuery {
        conditions.forEach {
            whereConditions.add(And(it))
        }
        return this
    }

    /**
     * Holds the columns for the GROUP BY clause
     *
     * @param cols            One or more column names
     */
    fun groupBy(vararg cols: String): TPSQLiteQuery {
        groupBys.addAll(cols as Array<String>)
        return this
    }

    /**
     * Holds the column for the GROUP BY clause
     *
     * @param col             Name of the column
     */
    fun groupBy(col: String): TPSQLiteQuery {
        groupBys.add(col)
        return this
    }

    /**
     * Holds the columns for the GROUP BY clause
     *
     * @param cols            List of column names
     */
    fun groupBy(cols: List<String>): TPSQLiteQuery {
        groupBys.addAll(cols)
        return this
    }

    /**
     * Holds the condition for the HAVING clause of the query
     *
     * @param condition       Object of TPSQLiteCondition
     *
     * @see TPSQLiteCondition
     */
    fun having(condition: TPSQLiteCondition): TPSQLiteQuery {
        this.havingConditions.add(And(condition.toString()))
        return this
    }

    /**
     * Holds the conditions for the HAVING clause of the query
     *
     * @param useOr           Boolean that defines whether the conditions are linked via an AND or an OR.
     * @param conditions      Objects of TPSQLiteCondition
     *
     * @see TPSQLiteCondition
     */
    fun having(useOr: Boolean, vararg conditions: TPSQLiteCondition): TPSQLiteQuery {
        if (useOr) {
            conditions.forEach {
                whereConditions.add(Or(it.toString()))
            }
        } else {
            conditions.forEach {
                whereConditions.add(And(it.toString()))
            }
        }
        return this
    }

    /**
     * Holds the conditions for the HAVING clause of the query
     *
     * @param conditions      Objects of TPSQLiteCondition
     *
     * @see TPSQLiteCondition
     */
    fun having(vararg conditions: TPSQLiteCondition): TPSQLiteQuery {
        conditions.forEach {
            whereConditions.add(And(it.toString()))
        }
        return this
    }

    /**
     * Holds the condition for the HAVING clause of the query
     *
     * @param condition       String representation of the condition
     */
    fun having(condition: String): TPSQLiteQuery {
        this.havingConditions.add(And(condition))
        return this
    }

    /**
     * Holds the conditions for the HAVING clause of the query
     *
     * @param useOr           Boolean that defines whether the conditions are linked via an AND or an OR.
     * @param conditions      One or more string representations of the condition
     */
    fun having(useOr: Boolean = false, vararg conditions: String): TPSQLiteQuery {
        if (useOr) {
            conditions.forEach {
                whereConditions.add(Or(it))
            }
        } else {
            conditions.forEach {
                whereConditions.add(And(it))
            }
        }
        return this
    }

    /**
     * Holds the conditions for the HAVING clause of the query
     *
     * @param conditions      One or more string representations of the condition
     */
    fun having(vararg conditions: String): TPSQLiteQuery {
        conditions.forEach {
            whereConditions.add(And(it))
        }
        return this
    }

    /**
     * Holds the columns for the ORDER BY clause
     *
     * @param cols            One or more column names
     */
    fun orderBy(vararg cols: String): TPSQLiteQuery {
        cols.forEach {
            orderBys.add(TPSQLiteSort.asc(it))
        }
        return this
    }

    /**
     * Holds the columns for the ORDER BY clause
     *
     * @param cols            One or more objects TPSQLiteSort
     *
     * @see TPSQLiteSort
     */
    fun orderBy(vararg cols: TPSQLiteSort): TPSQLiteQuery {
        cols.forEach {
            orderBys.add(it)
        }
        return this
    }

    /**
     * Holds the columns for the ORDER BY clause in ascending order
     *
     * @param cols            One or more column names
     */
    fun orderByAsc(vararg cols: String): TPSQLiteQuery {
        cols.forEach {
            orderBys.add(TPSQLiteSort.asc(it))
        }
        return this
    }

    /**
     * Holds the columns for the ORDER BY clause in descending order
     *
     * @param cols            One or more column names
     */
    fun orderByDesc(vararg cols: String): TPSQLiteQuery {
        cols.forEach {
            orderBys.add(TPSQLiteSort.desc(it))
        }
        return this
    }

    /**
     * Holds the number of rows for the LIMIT clause
     *
     * @param rowCount        Number of rows to be constraint
     * @param offset          Number from which the result set should start
     */
    fun limit(rowCount: Int, offset: Int = 0): TPSQLiteQuery {
        limitOffset = arrayOf(rowCount, offset)
        return this
    }

    private fun compileSelect(): String {
        val selectCols = mutableListOf<String>()
        for ((tbl, cols) in colsToSelect) {
            if (tbl == "") {
                selectCols.add(cols.joinToString(", "))
            } else {
                cols.forEach {
                    selectCols.add("$tbl.$it")
                }
            }
        }
        return selectCols.joinToString(", ")
    }

    private fun compileFrom(): String {
        var result = ""
        from.forEachIndexed { index, tbl ->
            if (index == this.from.size - 1) result += tbl
            else result += "$tbl, "
        }
        return result
    }

    private fun compileWhere(): String {
        var result = ""
        whereConditions.forEach {
            result += " ${it.getStatement()} ${it.getOperator()}"
        }
        if (result != "") {
            result = result.substring(0, result.length - 3).trim()
        }
        return  result
    }

    private fun compileJoins(): String {
        var result = ""
        for ((join, conditions) in tablesToJoin) {
            val parts = join.split("-")
            val tbl = parts.last()
            val joinStmt = parts.subList(0, parts.size - 1).joinToString(" ")

            var joinConditions = ""
            conditions.forEach {
                joinConditions += " ${it.getStatement()} ${it.getOperator()}"
            }

            if (joinConditions != "") {
                joinConditions = joinConditions.substring(0, joinConditions.length - 3)
                result += "$joinStmt $tbl ON ($joinConditions)"
            }
        }
        return result
    }

    private fun compileGroupBy(): String {
        return groupBys.joinToString(", ")
    }

    private fun compileHaving(): String {
        var result = ""
        havingConditions.forEachIndexed { index, stmt ->
            result += " ${stmt.getStatement()} ${stmt.getOperator()}"
        }
        if (result != "") {
            result = result.substring(0, result.length - 3).trim()
        }
        return  result
    }

    private fun compileOrderBy(): String {
        var result = ""
        orderBys.forEach {
            result += "${it.col} ${it.direction}, "
        }
        if (result != "") {
            result = result.substring(0, result.length - 2)
        }
        return result
    }

    private fun compileLimit(): String {
        var result = ""
        if (limitOffset != null) {
           result = "${limitOffset!![0]} OFFSET ${limitOffset!![1]}"
        }
        return result
    }
}