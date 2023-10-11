package ru.levgrekov.polynomial.ru.levgrekov.polynomial.math

import ru.levgrekov.polynomial.math.Polynomial

class NewtonPolynomial2(points: Map<Double,Double>) : Polynomial() {

    private val n: Int
        get() = _points.size

    private val _points: MutableList<Pair<Double,Double>>

    private var lastFundPoly: Polynomial;
    init {
        _points = mutableListOf()
        lastFundPoly = Polynomial(1.0)
        _coeffs[0] = 0.0
        this.addPoints(points.toList().toMutableList())
    }
    private fun fundPoly(j: Int): Polynomial {
        if (j != 1) {
            lastFundPoly = lastFundPoly * Polynomial(-_points[j-2].first, 1.0)
        }
        return lastFundPoly
    }

    private fun dividedDifference(k: Int): Double = (0..k).sumOf { j ->
        val multiplication = (0..k)
            .filter { i -> i != j }
            .fold(1.0) { acc, i -> acc * (_points[j].first - _points[i].first)}
        _points[j].second / multiplication
    }
    fun addPoint(x: Double, f: Double) {
        _points.add(Pair(x, f))
        this += fundPoly(n) * dividedDifference(n - 1)
    }
    fun addPoints( pointsList: List<Pair<Double,Double>>) =
        pointsList.forEach{this.addPoint(it.first,it.second)}
}

