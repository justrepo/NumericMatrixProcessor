package processor

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    val aN = scanner.nextInt()
    val aM = scanner.nextInt()
    val sum = Array(aN) { IntArray(aM) }

    for (i in 0 until aN) {
        for (j in 0 until aM) {
            sum[i][j] = scanner.nextInt()
        }
    }

    val bN = scanner.nextInt()
    if (aN != bN) {
        println("ERROR")
        return
    }
    val bM = scanner.nextInt()
    if (aM != bM) {
        println("ERROR")
        return
    }
    for (row in sum) {
        for (elem in row) {
            print("${elem + scanner.nextInt()} ")
        }
        println()
    }
}
