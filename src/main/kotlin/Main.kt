package ru.levgrekov.polynomial

import ru.levgrekov.polynomial.math.Polynomial
import kotlin.random.Random

//var t: Polynomial? = null
fun main() {
    val p1 = Polynomial(mapOf(0 to 5.0, 2 to -3.0, 5 to 1.0, 10 to 3.0,15 to 1.0, 7 to 1.0))
    println(p1)
    println(p1.minorDegree)
    println(p1.highDegree)

    val p2 = Polynomial(8.0, -6.0, -5.0, 3.0)
    val p22 = Polynomial(mapOf( 1 to -6.0, 0 to 8.0, 2 to -5.0,3 to 3.0))
    val p3 = Polynomial(-4.0, 1.0, 3.0)

    println(p2)
    println(p3)
    println(p22/p3)
    println(p2/p3)

//
//    val rnd = Random.nextBoolean()
//    if (rnd) t = Polynomial(3.0, 9.0, 5.0)
//    println(t?.let {
//        print("Количество коэффициентов: ")
//        it.coeffs.size
//    })
//
//    val p = Polynomial(3.5, 5.3).also { println(it) }
//    val pp = Polynomial(2.3).apply {
//        a = 3
//        b = 7
//    }
}