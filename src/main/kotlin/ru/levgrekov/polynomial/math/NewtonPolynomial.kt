package ru.levgrekov.polynomial.ru.levgrekov.polynomial.math

import ru.levgrekov.polynomial.math.Polynomial

class NewtonPolynomial(points: Map<Double,Double>) : Polynomial() {
    private val _points: MutableMap<Double, Double>
    init {
        _points = points.toMutableMap()
        if(_points.isEmpty()) _coeffs[0] = 0.0
        else _coeffs.apply {
            clear()
            putAll(CreateNewtonPoly().coeffs)
        }
    }

    private fun CreateNewtonPoly(): Polynomial {
        TODO("not yet")
    }


}