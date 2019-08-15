package knotbook.formulas.function

import knotbook.formulas.token.Ch
import knotbook.formulas.token.Name
import knotbook.formulas.token.tokenize
import split


fun def(rawExpr: String): Pair<String, Formula> {
    val expr = rawExpr.tokenize()
    val funcName = (expr[0] as Name).name
    val end = expr.indexOf(Ch(')'))
    val funcVars = expr.slice(2 until end)
            .split(Ch(','))
            .map { it[0] as Name }

    return funcName to ExpressionFormula(expr.slice((end + 2) until expr.size), funcVars)
}


fun main() {
    println(def("max(a, b): a*(a>b | a=b) + b*(b>a)"))
}
