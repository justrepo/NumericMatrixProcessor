package processor

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    val n = scanner.nextInt()
    val m = scanner.nextInt()
    val result = Array(n) { IntArray(m) }

    for (i in 0 until n) {
        for (j in 0 until m) {
            result[i][j] = scanner.nextInt()
        }
    }

    val c = scanner.nextInt()

    for (row in result) {
        for (elem in row) {
            print("${c * elem} ")
        }
        println()
    }
}
