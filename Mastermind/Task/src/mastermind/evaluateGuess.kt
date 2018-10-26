package mastermind

data class Evaluation(val positions: Int, val letters: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    //println("secret: $secret \nguess:  $guess")
    val (correctPositionsAmount, reducedSecret, reducedGuess) =
            positionsGuessedCorrectly(secret, guess)
    val correctLettersAmount =
            lettersGuessedCorrectly(reducedSecret, reducedGuess)

    return Evaluation(correctPositionsAmount, correctLettersAmount)
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
    val indexesToRemove = identifyIndexesToRemove(guess, secret)
    var (reducedSecret, reducedGuess) =
            reduceStringsRemovingIndexesFrom(secret, guess, indexesToRemove)

    val correctGuesses = indexesToRemove.size
    return Triple(correctGuesses, reducedSecret, reducedGuess)
}

private fun identifyIndexesToRemove(guess: String, secret: String): MutableList<Int> {
    val indexesToRemove = mutableListOf<Int>()
    for (guessedPair in guess.withIndex()) {
        val guessedIndex = guessedPair.index
        val secretLetter = secret.get(guessedIndex)
        if (guessedPair.value == secretLetter) {
            indexesToRemove.add(guessedIndex)
        }
    }
    return indexesToRemove
}

private fun reduceStringsRemovingIndexesFrom(secret: String,
                                             guess: String,
                                             indexesToRemove: MutableList<Int>): Pair<String, String> {
    var currentSecret = secret
    var currentGuess = guess
    //Need to start removing from higher to lower, otherwise it could access an unknown index
    indexesToRemove.reversed().forEach {
        currentGuess = currentGuess.replaceRange(it, it + 1, "")
        currentSecret = currentSecret.replaceRange(it, it + 1, "")
    }
    return Pair(currentSecret, currentGuess)
}
