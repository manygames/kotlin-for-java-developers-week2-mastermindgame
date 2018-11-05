package mastermind

data class Evaluation(val positions: Int, val letters: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {

    val positions = secret.zip(guess).count { it.first == it.second }

    val commonLetters = "ABCDEF".sumBy { ch ->
        Math.min(secret.count { it == ch }, guess.count { it == ch })
    }
    return Evaluation(positions, commonLetters - positions)
}

fun main(args: Array<String>) {
    val result = Evaluation(positions = 1, letters = 1)
    evaluateGuess("BCDF", "ACEB") eq result
    evaluateGuess("AAAF", "ABCA") eq result
    evaluateGuess("ABCA", "AAAF") eq result
}
infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}