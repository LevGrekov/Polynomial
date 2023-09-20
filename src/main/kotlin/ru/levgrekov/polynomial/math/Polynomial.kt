package ru.levgrekov.polynomial.math

import java.lang.StringBuilder
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.ulp

class Polynomial(coefficients: Map<Int, Double>) {
    private val _coeffs: MutableMap<Int, Double>;
    private var isZero: Boolean = false;
    init {
        _coeffs = coefficients.filter { (degree, value) -> value != 0.0 && degree >= 0}.toMutableMap();
        if (_coeffs.isEmpty()){
            _coeffs[0]=0.0;
            isZero = true;
        }
    }

    val size : Int = _coeffs.size;
    val highDegree : Int = _coeffs.keys.max();
    val minorDegree : Int = _coeffs.keys.min();



    constructor(vararg coeffs: Double) : this (coeffs.mapIndexed { index, value -> index to value }.toMap())
    constructor(coeffs: MutableList<Double>) : this (coeffs.mapIndexed { index, value -> index to value }.toMap())
    constructor(other: Polynomial) : this(HashMap(other._coeffs))

    operator fun times(scalar: Double) = Polynomial(_coeffs.map { (k, v) -> k to scalar * v }.toMap())


    operator fun div(scalar: Double) =
        Polynomial(_coeffs.map { (k, v) -> if (scalar eq 0.0) throw ArithmeticException("Division by zero") else k to 1.0 / scalar * v }
            .toMap())

    operator fun plus(other: Polynomial) = Polynomial(_coeffs.toMutableMap().also {
        other._coeffs.forEach { (k2, v2) -> it[k2] = v2 + (it[k2] ?: 0.0) }
    })

    operator fun minus(other: Polynomial) = Polynomial(_coeffs.toMutableMap().also {
        other._coeffs.forEach { (k2, v2) -> it[k2] = -v2 + (it[k2] ?: 0.0) }
    })

    operator fun times(other: Polynomial) = Polynomial(mutableMapOf<Int, Double>().also {
        _coeffs.forEach { (k1, v1) ->
            other._coeffs.forEach { (k2, v2) ->
                it[k1 + k2] = v1 * v2 + (it[k1 + k2] ?: 0.0)
            }
        }
    })

    operator fun invoke(scalar: Double) = _coeffs.entries.sumOf { (k, v) -> v * scalar.pow(k) }

    operator fun get(degree: Int) = _coeffs[degree] ?: 0.0

    override fun toString() = toString("x")

    private fun toString(arg: String) = buildString {

        _coeffs.toSortedMap(reverseOrder()).forEach { (degree, value) ->
            this.append(when {
                value < 0 -> " - "
                value > 0 && degree != highDegree -> " + "
                else -> ""
            })

            this.append(
                when (value) {
                    1.0 -> ""
                    else -> "${abs(value)}"
                }
            )

            this.append(
                when (degree) {
                    0 -> ""
                    1 -> "x"
                    else -> "x^$degree"
                }
            )
        }
    }

    override operator fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Polynomial) return false
        return this._coeffs == other._coeffs
    }
    override fun hashCode(): Int = _coeffs.keys.hashCode() * 17 + _coeffs.values.hashCode() * 31


}
fun Double.eq(other: Double, eps: Double) = abs(this - other) < eps

infix fun Double.eq(other: Double) = abs(this - other) < max(ulp, other.ulp) * 10.0

fun Double.neq(other: Double, eps: Double) = !this.eq(other, eps)

infix fun Double.neq(other: Double) = !this.eq(other)
