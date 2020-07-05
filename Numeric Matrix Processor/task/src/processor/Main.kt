package processor

import java.util.*
import kotlin.math.abs

val scanner = Scanner(System.`in`)

class Matrix(val n: Int, val m: Int) {
    companion object {
        fun read(name: String): Matrix {
            print("Enter size of $name: > ")
            val n = scanner.nextInt()
            val m = scanner.nextInt()
            val result = Matrix(n, m)

            println("Enter $name:")
            for (i in 0 until n) {
                print("> ")
                for (j in 0 until m) {
                    result.set(i, j, scanner.nextDouble())
                }
            }

            return result
        }
    }

    private val data = Array(n) { Array(m) { 0.0 } }

    fun at(i: Int, j: Int): Double {
        return data[i][j]
    }

    fun set(i: Int, j: Int, value: Double) {
        data[i][j] = value
    }

    fun printMatrix(name: String) {
        println("The $name is :")
        for (row in data) {
            for (elem in row) {
                print("%4.2f ".format(elem))
            }
            println()
        }
    }

    fun plus(second: Matrix): Matrix {
        if (n != second.n || m != second.m) {
            println("ERROR")
            return Matrix(0, 0)
        }

        val result = Matrix(n, m)
        for (rowIndex in 0 until n) {
            for (elementIndex in 0 until m) {
                result.data[rowIndex][elementIndex] = data[rowIndex][elementIndex] + second.data[rowIndex][elementIndex]
            }
        }

        return result
    }

    fun multiply(second: Double): Matrix {
        val result = Matrix(n, m)

        for (rowIndex in 0 until n) {
            for (elementIndex in 0 until m) {
                result.data[rowIndex][elementIndex] = data[rowIndex][elementIndex] * second
            }
        }

        return result
    }

    fun multiply(second: Matrix): Matrix {
        if (m != second.n) {
            println("ERROR")
            return Matrix(0, 0)
        }

        val result = Matrix(n, second.m)
        for (rowIndex in 0 until n) {
            for (columnIndex in 0 until second.m) {
                for (elementIndex in 0 until m) {
                    result.data[rowIndex][columnIndex] += data[rowIndex][elementIndex] * second.data[elementIndex][columnIndex]
                }
            }
        }

        return result
    }

    fun mainDiagonalTranspose(): Matrix {
        val result = Matrix(m, n)

        for (i in 0 until n) {
            for (j in 0 until m) {
                result.data[j][i] = data[i][j]
            }
        }

        return result
    }

    fun sideDiagonalTranspose(): Matrix {
        val result = Matrix(m, n)

        for (i in 0 until n) {
            for (j in 0 until m) {
                result.data[m - j - 1][n - i - 1] = data[i][j]
            }
        }

        return result
    }

    fun verticalLineTranspose(): Matrix {
        val result = Matrix(n, m)

        for (i in 0 until n) {
            for (j in 0 until m) {
                result.data[i][m - j - 1] = data[i][j]
            }
        }

        return result
    }

    fun horizontalLineTranspose(): Matrix {
        val result = Matrix(n, m)

        for (i in 0 until n) {
            for (j in 0 until m) {
                result.data[n - i - 1][j] = data[i][j]
            }
        }

        return result
    }

    private fun at(i: Int, j: Int, remainingXs: List<Int>, remainingYs: List<Int>): Double =
            data[remainingXs[i]][remainingYs[j]]

    private fun determinant(remainingXs: List<Int>, remainingYs: List<Int>): Double {
        return when (remainingYs.size) {
            0 -> 0.0
            1 -> at(0, 0, remainingXs, remainingYs)
            2 -> at(0, 0, remainingXs, remainingYs) * at(1, 1, remainingXs, remainingYs) -
                    at(1, 0, remainingXs, remainingYs) * at(0, 1, remainingXs, remainingYs)
            else -> {
                var sum = 0.0
                for (i in remainingYs.indices) {
                    sum += (if (i % 2 == 0) 1 else -1) *
                            determinant(remainingXs.drop(1), remainingYs.filterIndexed { index, _ -> index != i }) *
                            at(0, i, remainingXs, remainingYs)
                }
                sum
            }
        }
    }

    fun determinant(): Double {
        if (n != m) {
            print("ERROR")
            return 0.0
        }
        return when (n) {
            1 -> data[0][0]
            2 -> data[0][0] * data[1][1] - data[1][0] * data[0][1]
            else -> determinant(List(n) { i -> i }, List(n) { i -> i })
        }
    }

    fun inverse(): Matrix {
        if (n != m) {
            print("ERROR")
            return Matrix(0, 0)
        }
        val det = determinant()
        if (abs(det) < 1e-10) {
            print("ERROR")
            return Matrix(0, 0)
        }
        val result = Matrix(n, n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                result.data[j][i] = (if ((i + j) % 2 == 0) 1 else -1) *
                            determinant(List(n) { index -> index }.filterIndexed { index, _ -> index != i },
                                List(n) { index -> index }.filterIndexed { index, _ -> index != j }) / det
            }
        }

        return result
    }
}

fun readDouble(name: String): Double {
    print("Enter $name: > ")
    return scanner.nextDouble()
}

fun main() {
    do {
        print("1. Add matrices\n" +
                "2. Multiply matrix to a constant\n" +
                "3. Multiply matrices\n" +
                "4. Transpose matrix\n" +
                "5. Calculate a determinant\n" +
                "6. Inverse matrix\n" +
                "0. Exit\n" +
                "Your choice: > ")
        val choice = scanner.nextInt()
        when (choice) {
            1 -> Matrix.read("first matrix").plus(Matrix.read("second matrix")).printMatrix("sum")
            2 -> Matrix.read("matrix").multiply(readDouble("constant")).printMatrix("multiplication result")
            3 -> Matrix.read("first matrix").multiply(Matrix.read("second matrix")).printMatrix("multiplication result")
            4 -> {
                print("1. Main diagonal\n" +
                        "2. Side diagonal\n" +
                        "3. Vertical line\n" +
                        "4. Horizontal line\n" +
                        "Your choice: > ")
                when (scanner.nextInt()) {
                    1 -> Matrix.read("matrix").mainDiagonalTranspose().printMatrix("result")
                    2 -> Matrix.read("matrix").sideDiagonalTranspose().printMatrix("result")
                    3 -> Matrix.read("matrix").verticalLineTranspose().printMatrix("result")
                    4 -> Matrix.read("matrix").horizontalLineTranspose().printMatrix("result")
                }
            }
            5 -> println("The result is:\n${Matrix.read("matrix").determinant()}")
            6 -> Matrix.read("matrix").inverse().printMatrix("result")
        }
    } while (choice != 0)
}
