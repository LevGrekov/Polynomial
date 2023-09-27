package ru.levgrekov.polynomial.ru.levgrekov.polynomial.math

import ru.levgrekov.polynomial.math.Polynomial

class NewtonPolynomial2(points: Map<Double,Double>) : Polynomial() {

    private val n: Int
        get() = _points.size

    private val _points: MutableList<Pair<Double,Double>>
    init {
        _points = points.toList().toMutableList()

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
        acc * Polynomial(-_points[i].first, 1.0) }
//    private fun dividedDifference(k : Int): Double{
//        var result = 0.0;
//        for (j in 0..k) {
//            var multiplication = 1.0
//            for (i in 0..k) {
//                if (i != j) multiplication *= (xValues[j] - xValues[i])
//            }
//            result += fValues[j] / multiplication
//        }
//        return result
//    }
    private fun dividedDifference(k: Int): Double = (0..k).sumOf { j ->
        val multiplication = (0..k)
            .filter { i -> i != j }
            .fold(1.0) { acc, i -> acc * (_points[j].first - _points[i].first)}
        _points[j].second / multiplication
    }

//    private fun createNewtonPoly() : Polynomial{
//        val result = Polynomial(0.0)
//        for (i in 0..<n){
//            result += createFundamentalPoly(i) * dividedDifference(i)
//        }
//        return result
//    }
    private fun createNewtonPoly() : Polynomial = (0..<n)
        .map { i -> createFundamentalPoly(i) * dividedDifference(i) }
        .reduce { acc, poly -> acc + poly }

    fun addPoint(x: Double, f: Double) = _points.add(Pair(x, f))
        .also { this += createFundamentalPoly(n - 1) * dividedDifference(n - 1) }

}