package ru.levgrekov.polynomial.ru.levgrekov.polynomial.math

import ru.levgrekov.polynomial.math.Polynomial

class NewtonPolynomial(points: Map<Double,Double>) : Polynomial() {
    private val _points: MutableMap<Double, Double>
    private val n: Int
        get() = _points.size

    private val xValues
        get() = _points.keys.toList()

    private val fValues
        get() = _points.values.toList()

    private val dividedDifferenceTable : Array<DoubleArray>

    init {
        _points = points.toMutableMap()
        dividedDifferenceTable = createDividedDifferenceTable();
        if(_points.isEmpty()) _coeffs[0] = 0.0
        else _coeffs.apply {
            clear()
            putAll(createNewtonPoly().coeffs)

        }
    }

    private fun addPoint(){

    }

    private fun createDividedDifferenceTable(): Array<DoubleArray> = Array(n) { DoubleArray(n) }.also{
        table ->  repeat(n) { i -> table[i][0] = fValues[i] }
        (1..<n).forEach { j ->
            (0..<n - j).forEach { i ->
                table[i][j] = (table[i + 1][j - 1] - table[i][j - 1]) / (xValues[i + j] - xValues[i])
            }
        }
    }



    private fun createNewtonPoly(): Polynomial =
        (1..<n).fold(Polynomial(mapOf(0 to dividedDifferenceTable[0][0]))) { acc, j ->
            acc + (0..<j).fold(Polynomial(1.0)) { term, i -> term * Polynomial(-xValues[i], 1.0) } * dividedDifferenceTable[0][j] }


    //    private fun createDividedDifferenceTable(): Array<DoubleArray> {
//        val dividedDifferenceTable = Array(n) { DoubleArray(n) }
//        for (i in 0..<n) {
//        dividedDifferenceTable[i][0] = fValues[i]
//        }
//
//        for (j in 1..< n) {
//            for (i in 0..< n - j) {
//                dividedDifferenceTable[i][j] =
//                    (dividedDifferenceTable[i + 1][j - 1] - dividedDifferenceTable[i][j - 1]) / (xValues[i + j] - xValues[i])
//            }
//        }
//        return dividedDifferenceTable
//    }


//    private fun createNewtonPoly(): Polynomial {
//        val dividedDifferenceTable = createDividedDifferenceTable()
//        val result = Polynomial(mapOf(0 to dividedDifferenceTable[0][0]))
//
//        for (j in 1..<n) {
//            val term = Polynomial(1.0)
//            for (i in 0..<j) {
//                term *= Polynomial(-xValues[i], 1.0)
//            }
//            result += term * dividedDifferenceTable[0][j]
//        }
//
//        return result
//    }

}