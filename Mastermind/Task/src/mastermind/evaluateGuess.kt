package mastermind

data class Evaluation(val positions: Int, val letters: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    //println("secret: $secret \nguess:  $guess")
    val (amountOf, currentSecret, currentGuess) = positionsGuessedCorrectly(secret, guess)
    val numberOf = lettersGuessedCorrectly(currentSecret, currentGuess)
    return Evaluation(amountOf, numberOf)
}

fun lettersGuessedCorrectly(secret: String, guess: String): Int {
    var correctGuesses = 0
    for (ch in guess) {
        if (secret.contains(ch))
            correctGuesses++
    }
    return correctGuesses
}

fun positionsGuessedCorrectly(secret: String, guess: String): Triple<Int, String, String> {
    var correctGuesses = 0
    val indexesToRemove = mutableListOf<Int>()
    for (guessedPair in guess.withIndex()) {
        val guessIndex = guessedPair.index
        val secretLetter = secret.get(guessIndex)
        if (guessedPair.value == secretLetter) {
            indexesToRemove.add(guessIndex)
            correctGuesses++
        }
    }

    var (currentSecret, currentGuess) =
            generateReducedStringsRemovingMatchLetters(secret, guess, indexesToRemove)

    return Triple(correctGuesses, currentSecret, currentGuess)
}

private fun generateReducedStringsRemovingMatchLetters(secret: String,
                                                       guess: String,
                                                       indexesToRemove: MutableList<Int>): Pair<String, String> {
    var currentSecret = secret
    var currentGuess = guess
    indexesToRemove.reversed().forEach {
        currentGuess = currentGuess.replaceRange(it, it + 1, "")
        currentSecret = currentSecret.replaceRange(it, it + 1, "")
    }
    return Pair(currentSecret, currentGuess)
}
