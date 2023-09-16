package ru.levgrekov.polynomial.math

import java.lang.StringBuilder
import kotlin.math.abs
import kotlin.math.pow

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
        this.highDegree = coefficientsMap.keys.maxOrNull() ?: 0
        this.minorDegree = coefficientsMap.keys.minOrNull() ?: 0
    }

    constructor(vararg coeffs: Double) : this (coeffs.mapIndexed { index, value -> index to value }.toMap())
    constructor(coeffs: MutableList<Double>) : this (coeffs.mapIndexed { index, value -> index to value }.toMap())
    constructor(other: Polynomial) : this(HashMap(other.coefficientsMap))
    override fun toString(): String {
        val sb = StringBuilder();
        val sortedMap = coefficientsMap.toSortedMap(reverseOrder());

        for ((degree,value) in sortedMap){
            //sb.append("${ if (value > 0) " + " else " - " }${ if (value!=1.0) abs(value) else ""}${if (degree!=0) "x^$degree" else ""}");

            val sign = when{
                value < 0 -> " - ";
                value > 0 && degree != highDegree -> " + ";
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
    operator fun div(scalar: Double) = times(1/scalar)

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

    private fun divide(divisor: Polynomial): Pair<Polynomial,Polynomial> {
        if(isZero) throw ArithmeticException("Старший член многочлена делимого не может быть 0");
        if(divisor.isZero) throw  ArithmeticException("Старший член многочлена делителя не может быть 0");

        val divisorList = (0..divisor.highDegree).map {divisor.coefficientsMap.getOrDefault(it,0.0)}.toMutableList()
        val remainder = (0..this.highDegree).map {coefficientsMap.getOrDefault(it,0.0)}.toMutableList()

        val quotient = MutableList(remainder.size - divisor.size + 1){0.0}

        for(i in quotient.indices){
            val coeff : Double = remainder[remainder.size - i - 1] / divisorList.last();
            quotient[quotient.size - i - 1] = coeff;

            for(j in divisorList.indices){
                remainder[remainder.size - i - j - 1] -= coeff * divisorList[divisorList.size - j - 1]
            }
        }

        return Pair(Polynomial(quotient),Polynomial(remainder))
    }

    operator fun rem(other:Polynomial) = divide(other).second;
    operator fun div(other: Polynomial) = divide(other).first;

//    operator fun invoke(x: Double) : Double{
//        var result = 0.0;
//        for ( (degree, value) in coefficientsMap){
//            result += value * Math.pow(x,degree.toDouble());
//        }
//        return result;
//    }
//

    operator fun invoke(x : Double) = coefficientsMap.entries.sumOf { (k,v) -> v * x.pow(k) }
}