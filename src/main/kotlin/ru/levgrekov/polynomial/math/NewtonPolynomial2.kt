package ru.levgrekov.polynomial.ru.levgrekov.polynomial.math

import ru.levgrekov.polynomial.math.Polynomial
import java.util.*

class NewtonPolynomial2(points: Map<Double,Double>) : Polynomial() {
    private val _points: SortedMap<Double,Double>
    private val n: Int
        get() = xValues.size

    private val xValues: MutableList<Double>

    private val fValues: MutableList<Double>
    init {
        _points = points.toSortedMap()
        xValues = points.keys.toMutableList()
        fValues = points.values.toMutableList()

        if(points.isEmpty()) _coeffs[0] = 0.0
        else _coeffs.apply {
            clear()
            putAll(createNewtonPoly().coeffs)
        }
    }



//    private fun createFundamentalPoly(j: Int): Polynomial {
//        val result = Polynomial(1.0);
//        for(i in 0..<j){
//            result *= Polynomial(-xValues[i],1.0)
//        }
//        return result;
//    }

    private fun createFundamentalPoly(j: Int): Polynomial = (0..<j).fold(Polynomial(1.0)) { acc, i ->
        acc * Polynomial(-xValues[i], 1.0) }

    private fun dividedDifference(k : Int): Double{
        var result = 0.0;
        for (j in 0..k) {
            var mult = 1.0
            for (i in 0..k) {
                if (i != j) mult *= (xValues[j] - xValues[i])
            }
            result += fValues[j] / mult
        }
        return result
    }

//    private fun dividedDifference(k : Int) : Double =
//        (0..k).map { j ->
//            (0..k).filter { i -> i != j }.fold(1.0) { mult, i ->
//                mult * (xValues[j] - xValues[i])
//            }
//        }.sumOf { fValues[it] } / (1..k).reduce { acc, value -> acc * value }

    private fun createNewtonPoly() : Polynomial{
        val result = Polynomial(0.0)
        for (i in 0..<n){
            result += createFundamentalPoly(i) * dividedDifference(i)
        }
        return result
    }

    fun addPoint(x: Double, f: Double){
        xValues.add(x)
        fValues.add(f)
        this += createFundamentalPoly(n-1) * dividedDifference(n-1)
    }
}