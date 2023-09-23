package ru.levgrekov.polynomial.ru.levgrekov.polynomial.math
import ru.levgrekov.polynomial.math.Polynomial

class LagrangePolynomial(points: Map<Double,Double>) : Polynomial() {
    private val _points: MutableMap<Double, Double>
    init {
        _points = points.toMutableMap()
        if(_points.isEmpty()) _coeffs[0] = 0.0
        else _coeffs.apply {
            clear()
            putAll(createLagrangePoly().coeffs)
        }
    }
    val points: Map<Double,Double>
        get()= _points.toMap()


//    private fun createLagrangePoly(): Polynomial {
//        var result = Polynomial(mapOf(0 to 0.0))
//        _points.forEach { (x, fx) -> result += createFundamentalPoly(x) * fx }
//        return result;
//    }

    private fun createLagrangePoly(): Polynomial = _points.entries.fold(Polynomial(mapOf(0 to 0.0))) {result, (x,fx) -> result + (createFundamentalPoly(x) * fx)}

    private fun createFundamentalPoly(xk: Double): Polynomial =
        _points.keys.fold(Polynomial(1.0)) { acc, it -> if (xk neq it) acc * Polynomial(-it, 1.0) / (xk - it) else acc }

}