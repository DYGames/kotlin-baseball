package baseball

import camp.nextstep.edu.missionutils.Console
import camp.nextstep.edu.missionutils.Randoms
import kotlin.text.StringBuilder

enum class Score {
    NOTHING,
    STRIKE,
    BALL
}

fun exitGame(strike: Int): Boolean {
    if (strike == 3) {
        println("3개의 숫자를 모두 맞히셨습니다! 게임 종료")
        println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.")
        val flag = getUserInput(true)
        if (flag == "1")
            return false
        else if (flag == "2")
            return true
    }
    return false
}

fun processGame() {
    println("숫자 야구 게임을 시작합니다.")
    val randomNumber = generateRandomNumber()
    println(randomNumber)
    while (true) {
        print("숫자를 입력해주세요 : ")
        val userNumber = getUserInput(false)
        val scores = determineScore(randomNumber, userNumber)
        val formattedScore = formatScore(scores)
        println(formattedScore)
        if (exitGame(scores[Score.STRIKE.ordinal]))
            break
    }
}

fun formatScore(scores: List<Int>): String {
    val formattedScore = StringBuilder()
    if (scores[Score.BALL.ordinal] != 0)
        formattedScore.append("${scores[Score.BALL.ordinal]}볼")
    if (scores[Score.STRIKE.ordinal] != 0)
        formattedScore.append("${scores[Score.STRIKE.ordinal]}스트라이크")
    if (scores[Score.BALL.ordinal] == 0 && scores[Score.BALL.ordinal] == 0 && scores[Score.NOTHING.ordinal] != 0)
        return "낫싱"
    return formattedScore.toString()
}

fun determineScore(randomNumber: String, userNumber: String): List<Int> {
    val scores = mutableListOf(0, 0, 0)
    for (i in 0..2) {
        val score = compareNumber(randomNumber, userNumber, i)
        scores[score.ordinal] += 1
    }
    return scores
}

fun compareNumber(randomNumber: String, userNumber: String, index: Int): Score {
    if (randomNumber[index] == userNumber[index])
        return Score.STRIKE
    else if (randomNumber.contains(userNumber[index]))
        return Score.BALL
    return Score.NOTHING
}

fun generateRandomNumber(): String {
    val numbers = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val randomNumber = StringBuilder()
    for (i in 0..2) {
        val index = Randoms.pickNumberInRange(1, numbers.size - 1)
        randomNumber.append(numbers[index])
        numbers.removeAt(index)
    }
    return randomNumber.toString()
}

fun getUserInput(mode: Boolean): String {
    val input = Console.readLine()
    if (input == "1" || input == "2") {
        if (mode)
            return input
        else if (!mode)
            throw IllegalArgumentException()
    }
    if (input.length != 3)
        throw IllegalArgumentException()
    if (!input.all { char -> char.isDigit() })
        throw IllegalArgumentException()
    return input
}

fun main() {
    processGame()
}
