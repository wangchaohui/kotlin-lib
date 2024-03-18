class LinearProgramming {
    // Ax <= b
    class Constraint(
        val a: DoubleArray,
        val b: Double,
    )

    //    val objectiveFunction: DoubleArray,
    //    val constraints: Array<DoubleArray>,
    //    val rightHandSide: DoubleArray

    class SimplexTableau(
        private val matrix: Array<DoubleArray>,
        private val basicVariableOfConstraints: IntArray,
        private val nonBasicVariables: MutableSet<Int>,
    ) {
        private val numVariables = matrix[0].size - 1
        private val numConstraints = matrix.size - 1

        /** Find the column with the max coefficient in the objective row. */
        fun findPivotColumn(): Int? = nonBasicVariables
            .filter { matrix[0][it] > 0 }
            .maxByOrNull { matrix[0][it] }

        /** Find row with smallest non-negative ratio (`rightHandSide / constraint[row][enteringColumn]`) */
        fun findPivotRow(enteringColumn: Int): Int? = (1..numConstraints)
            .filter { matrix[it][enteringColumn] > 0 }
            .minByOrNull { matrix[it].last() / matrix[it][enteringColumn] }

        fun performPivot(pivotColumn: Int, pivotRow: Int) {
            for (j in matrix[0].indices) if (j != pivotColumn) {
                matrix[pivotRow][j] /= matrix[pivotRow][pivotColumn]
            }
            matrix[pivotRow][pivotColumn] = 1.0

            for (i in matrix.indices) if (i != pivotRow) {
                for (j in matrix[0].indices) if (j != pivotColumn) {
                    matrix[i][j] -= matrix[pivotRow][j] * matrix[i][pivotColumn]
                }
                matrix[i][pivotColumn] = 0.0
            }

            nonBasicVariables -= pivotColumn
            nonBasicVariables += basicVariableOfConstraints[pivotRow]
            basicVariableOfConstraints[pivotRow] = pivotColumn
        }

        /** Construct solution and objective value from the tableau */
        fun getSolution(): Pair<DoubleArray, Double> {
            val ans = DoubleArray(nonBasicVariables.size)
            for (i in 1..numConstraints) {
                if (basicVariableOfConstraints[i] < nonBasicVariables.size) {
                    ans[basicVariableOfConstraints[i]] = matrix[i].last()
                }
            }
            return ans to -matrix[0].last()
        }
    }

    fun solve(constraints: List<Constraint>, target: DoubleArray): Pair<DoubleArray, Double>? {
        check(constraints.all { it.a.size == target.size })
        val numVariables = constraints.size + target.size
        val matrix = Array(constraints.size + 1) { DoubleArray(numVariables + 1) }
        val basicVariableOfConstraints = IntArray(constraints.size + 1)
        for (j in target.indices) matrix[0][j] = target[j]
        for (i in constraints.indices) {
            for (j in target.indices) matrix[i + 1][j] = constraints[i].a[j]
            matrix[i + 1][target.size + i] = 1.0
            basicVariableOfConstraints[i + 1] = target.size + i
            matrix[i + 1][numVariables] = constraints[i].b
        }
        val tableau = SimplexTableau(matrix, basicVariableOfConstraints, target.indices.toMutableSet())

        while (true) {
            val pivotColumn = tableau.findPivotColumn() ?: break // No solution

            val pivotRow = tableau.findPivotRow(pivotColumn)
                ?: return DoubleArray(0) to Double.POSITIVE_INFINITY  // Unbounded solution

            tableau.performPivot(pivotColumn, pivotRow)
        }

        return tableau.getSolution()
    }

}
