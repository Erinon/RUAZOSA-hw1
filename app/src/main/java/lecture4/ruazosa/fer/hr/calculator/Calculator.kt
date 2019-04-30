package lecture4.ruazosa.fer.hr.calculator

/**
 * Created by dejannovak on 24/03/2018.
 */
object Calculator {

    var result: Double = 0.0
    private set

    var expression: MutableList<String> = mutableListOf()
    private set

    fun reset() {
        result = 0.0
        expression = mutableListOf()
    }

    fun addNumber(number: String) {
        try {
            val num = number.toDouble()
        } catch (e: NumberFormatException) {
            throw Exception("Not valid number")
        }

        if (expression.count() % 2 == 0) {
            expression.add(number)
        } else {
            throw Exception("Not a valid order of numbers in expression")
        }
    }

    fun addOperator(operator: String) {
        if (expression.count() % 2 != 1)  {
            throw Exception("Not a valid order of operator in expression")
        }

        when (operator) {
            "+", "-", "*", "/" -> expression.add(operator)
            else -> {
                throw Exception("Not a valid operator")
            }
        }
    }

    fun evaluate() {
        if (expression.count() % 2 == 0) {
            throw Exception("Not a valid expression")
        }

        while (expression.contains("*") || expression.contains("/")) {
            val indexMul = expression.indexOf("*")
            val indexDiv = expression.indexOf("/")

            if (indexDiv == -1 || (indexMul in 0 until indexDiv)) {
                expression[indexMul] = (expression[indexMul - 1].toDouble() * expression[indexMul + 1].toDouble()).toString()

                expression.removeAt(indexMul + 1)
                expression.removeAt(indexMul - 1)
            } else {
                if (expression[indexDiv + 1] == "0") {
                    throw Exception("Can not divide by zero")
                }

                expression[indexDiv] = (expression[indexDiv - 1].toDouble() / expression[indexDiv + 1].toDouble()).toString()

                expression.removeAt(indexDiv + 1)
                expression.removeAt(indexDiv - 1)
            }
        }

        result = expression[0].toDouble()

        for(i in 1 until expression.count() step 2) {
            when(expression[i]) {
                "+" -> result += expression[i + 1].toDouble()
                "-" -> result -= expression[i + 1].toDouble()
            }
        }
    }
}