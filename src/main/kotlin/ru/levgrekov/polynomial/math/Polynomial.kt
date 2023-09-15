package ru.levgrekov.polynomial.math

import java.lang.StringBuilder
import kotlin.math.abs

class Polynomial(coefficients: Map<Int, Double>) {
    private val coefficientsMap: MutableMap<Int, Double>;
    private var isZero: Boolean = false;
    public var size : Int;
    public var highDegree : Int;
    public var minorDegree : Int;
    init {
        coefficientsMap = coefficients.filter { (degree, value) -> value != 0.0 && degree >= 0}.toMutableMap();
        if (coefficientsMap.isEmpty()){
            coefficientsMap[0]=0.0;
            isZero = true;
        }
        this.size = coefficientsMap.size;
        this.highDegree = coefficientsMap.toSortedMap().lastKey();
        this.minorDegree = coefficientsMap.toSortedMap().firstKey();
    }
    constructor(vararg coeffs: Double) : this (coeffs.mapIndexed { index, value -> index to value }.toMap())
    constructor(other: Polynomial) : this(HashMap(other.coefficientsMap))
    override fun toString(): String {
        val sb = StringBuilder();
        val sortedMap = coefficientsMap.toSortedMap(reverseOrder());

        for ((degree,value) in sortedMap){
            //sb.append("${ if (value > 0) " + " else " - " }${ if (value!=1.0) abs(value) else ""}${if (degree!=0) "x^$degree" else ""}");

            val sign = when{
                value < 0 -> " - ";
                value > 0 && degree != sortedMap.firstKey() -> " + ";
                else -> "";
            }

            val v = when (value) {
                1.0 -> ""
                else -> "${abs(value)}"
            }

            val exp = when (degree) {
                0 -> ""
                1 -> "x"
                else -> "x^$degree"
            }

            sb.append(sign+v+exp)
        }

        return sb.toString();
    }

    operator fun times(scalar: Double) = Polynomial(coefficientsMap.map { (k, v) -> k to scalar*v }.toMap())

    operator fun plus(other: Polynomial) = Polynomial(coefficientsMap.toMutableMap().also {
        other.coefficientsMap.forEach{(k2,v2) -> it[k2] = v2 + (it[k2]?:0.0)}
    } )

    operator fun minus(other: Polynomial) = Polynomial(coefficientsMap.toMutableMap().also {
        other.coefficientsMap.forEach{(k2,v2) -> it[k2] = -v2 + (it[k2]?:0.0)}
    } )

    operator fun times(other: Polynomial) = Polynomial(mutableMapOf<Int, Double>().also {
        coefficientsMap.forEach{ (k1, v1) ->
            other.coefficientsMap.forEach { (k2, v2) ->
                it[k1+k2] = v1*v2 + (it[k1+k2]?:0.0)
            }
        }
    })
//
//    operator fun divide(divisor: Polynomial){
//        if(isZero) throw ArithmeticException("Старший член многочлена делимого не может быть 0");
//        if(divisor.isZero) throw  ArithmeticException("Старший член многочлена делителя не может быть 0");
//        val remainder = Polynomial(this);
//        val quotient: Polynomial;
//
//    }

}