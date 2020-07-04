package processor

import java.util.*

val scanner = Scanner(System.`in`)
fun readDouble(name: String): Double {
    print("Enter $name: > ")
    return scanner.nextDouble()
}

fun readMatrix(name: String): Array<Array<Double>> {
    print("Enter size of $name: > ")
    val n = scanner.nextInt()
    val m = scanner.nextInt()
    val result = Array(n) { Array(m) { .0 } }

    println("Enter $name:")
    for (i in 0 until n) {
        print("> ")
        for (j in 0 until m) {
            result[i][j] = scanner.nextDouble()
        }
    }

    return result
}

fun printMatrix(matrix: Array<Array<Double>>, name: String) {
    println("The $name is :")
    for (row in matrix) {
        for (elem in row) {
            print("$elem ")
        }
        println()
    }
}

fun sumMatrices(matrix1: Array<Array<Double>>, matrix2: Array<Array<Double>>): Array<Array<Double>> {
    if (matrix1.size != matrix2.size) {
        println("ERROR")
        return Array(1) { Array(1) { .0 } }
    }

    for (rowIndex in matrix1.indices) {
        for (elementIndex in matrix1[rowIndex].indices) {
            matrix1[rowIndex][elementIndex] += matrix2[rowIndex][elementIndex]
        }
    }

    return matrix1
}

fun multiplyMatrixToAConstant(matrix: Array<Array<Double>>, constant: Double): Array<Array<Double>> {
    for (rowIndex in matrix.indices) {
        for (elementIndex in matrix[rowIndex].indices) {
            matrix[rowIndex][elementIndex] *= constant
        }
    }

    return matrix
}

fun multiplyMatrices(matrix1: Array<Array<Double>>, matrix2: Array<Array<Double>>): Array<Array<Double>> {
    if (matrix1[0].size != matrix2.size) {
        println("ERROR")
        return Array(1) { Array(1) { .0 } }
    }
    val result = Array(matrix1.size) { Array(matrix2[0].size) { .0 } }

    for (rowIndex in matrix1.indices) {
        for (columnIndex in matrix2[0].indices) {
            for (elementIndex in matrix1[rowIndex].indices) {
                result[rowIndex][columnIndex] += matrix1[rowIndex][elementIndex] * matrix2[elementIndex][columnIndex]
            }
        }
    }

    return result
}

fun main() {
    do {
        print("1. Add matrices\n" +
                "2. Multiply matrix to a constant\n" +
                "3. Multiply matrices\n" +
                "0. Exit\n" +
                "Your choice: > ")
        val choice = scanner.nextInt()
        when (choice) {
            1 -> printMatrix(sumMatrices(readMatrix("first matrix"), readMatrix("second matrix")), "sum")
            2 -> printMatrix(multiplyMatrixToAConstant(readMatrix("matrix"), readDouble("constant")), "multiplication result")
            3 -> printMatrix(multiplyMatrices(readMatrix("first matrix"), readMatrix("second matrix")), "multiplication result")
        }
    } while (choice != 0)
}
