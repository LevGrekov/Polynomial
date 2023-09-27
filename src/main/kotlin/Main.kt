package ru.levgrekov.polynomial


import ru.levgrekov.polynomial.ru.levgrekov.polynomial.math.LagrangePolynomial
import ru.levgrekov.polynomial.ru.levgrekov.polynomial.math.NewtonPolynomial2
import kotlin.random.Random
import kotlin.system.measureTimeMillis

//var t: Polynomial? = null
fun main() {
//    val p1 = Polynomial(mapOf(0 to 5.0, 2 to -3.0, 5 to 1.0, 10 to 3.0,15 to 1.0, 7 to 1.0))
//    println(p1)
//    println(p1.minorDegree)
//    println(p1.highDegree)
//
//    val p2 = Polynomial(8.0, -6.0, -5.0, 3.0)
//    val p22 = Polynomial(mapOf( 1 to -6.0, 0 to 8.0, 2 to -5.0,3 to 3.0))
//    val p3 = Polynomial(-4.0, 1.0, 3.0)
//    val z = Polynomial(0.0);
//        println(p2)
//        println(p3)
//        println(p22/p3)
//        println(p2)
//        println(p2 + z)
//
//    val lp = NewtonPolynomial(mapOf(-1.0 to 1.0, 1.0 to 1.0, 2.0 to 4.0, 3.0 to 9.0))
//    println("NP: $lp")
//
//    val np2 = NewtonPolynomial(mapOf(-2.0 to -8.0, -1.0 to -1.0, 0.0 to 0.00, 1.0 to 1.0, 2.0 to 10.0))
//    println("NP $np2")
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

    val a1 = LagrangePolynomial(mapOf(-2.0 to 4.0,0.0 to 0.0, 2.0 to 4.0, 3.0 to 9.0))
    println(a1)

    val a = NewtonPolynomial2(mapOf(-2.0 to 4.0,0.0 to 0.0, 2.0 to 4.0))
    a.addPoint(3.0,9.0)
    println(a)
    a.addPoint(4.0,16.0)
    println(a)

    comparePolynomials(200)

}

fun comparePolynomials(n: Int) {

    val points = mutableMapOf<Double, Double>()

    for (i in -n/2..<n/2) {
        val y = Random.nextDouble() * 100.0 - 50.0
        points[i*1.0] = y
    }

    val lagrangeTime = measureTimeMillis {
        val lagrange = LagrangePolynomial(points)
    }

    val newtonTime = measureTimeMillis {
        val newton = NewtonPolynomial2(points)
    }
    println("Для $n точек:")
    println("Время Построения  LagrangePolynomial: $lagrangeTime миллисекунд")
    println("Время Построения  NewtonPolynomial: $newtonTime миллисекунд")


}