package ru.levgrekov.polynomial.ru.levgrekov.polynomial.math

import ru.levgrekov.polynomial.math.Polynomial

class NewtonPolynomial2(points: Map<Double,Double>) : Polynomial() {

    private val n: Int
        get() = _points.size

    private val _points: MutableList<Pair<Double,Double>>

    private val lastFundPoly: Polynomial = Polynomial(1.0)
    init {
        _points = points.toList().toMutableList()

        if(points.isEmpty()) _coeffs[0] = 0.0
//        else {
//            val a = NewtonPolynomial2(mapOf())
//            a.addPoints(_points)
//
//            _coeffs.clear()
//            _coeffs.putAll(a.coeffs)
//        }

    }
    private fun createFundamentalPoly(j: Int){
        lastFundPoly *= Polynomial(-_points[j-1].first,1.0)
    }
//    private fun createFundamentalPoly(j: Int): Polynomial = (0..<j).fold(Polynomial(1.0)) { acc, i ->
//        acc * Polynomial(-_points[i].first, 1.0) }

//    private fun dividedDifference(k : Int): Double{
//        var result = 0.0;
//        for (j in 0..k) {
//            var multiplication = 1.0
//            for (i in 0..k) {
//                if (i != j) multiplication *= (_points[j].first - _points[i].first)
//            }
//            result += _points[j].second / multiplication
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
//    private fun createNewtonPoly() : Polynomial = (0..<n)
//        .map { i -> createFundamentalPoly(i) * dividedDifference(i) }
//        .reduce { acc, poly -> acc + poly }

    fun addPoint(x: Double, f: Double) = _points.add(Pair(x, f))
        .also {
            createFundamentalPoly(n)
            this += lastFundPoly * dividedDifference(n - 1) }

    fun addPoints( pointsList: List<Pair<Double,Double>>) =
        pointsList.forEach{this.addPoint(it.first,it.second)}
}

//4.440892098500626E-16x^3+1.0000000000000027x^2+4.884981308350689E-15x+2.6645352591003757E-15