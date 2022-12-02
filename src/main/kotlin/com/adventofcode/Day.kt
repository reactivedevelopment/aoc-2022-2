package com.adventofcode

import com.adventofcode.Figure.*
import com.adventofcode.Game.game
import com.adventofcode.Game.rightScores
import com.adventofcode.Round.*

enum class Round(val scores: Long) {
  Win(6),
  Lose(0),
  Draw(3),
}

enum class Figure(val scores: Long) {

  Rock(1) {
    override fun roundWith(other: Figure): Round {
      return when (other) {
        Rock -> Draw
        Scissors -> Win
        Paper -> Lose
      }
    }
  },

  Paper(2) {
    override fun roundWith(other: Figure): Round {
      return when (other) {
        Paper -> Draw
        Rock -> Win
        Scissors -> Lose
      }
    }
  },

  Scissors(3) {
    override fun roundWith(other: Figure): Round {
      return when (other) {
        Scissors -> Draw
        Rock -> Lose
        Paper -> Win
      }
    }
  };

  abstract fun roundWith(other: Figure): Round
}


fun parseFigure(s: String): Figure {
  return when (s) {
    "A" -> Rock
    "B" -> Paper
    "C" -> Scissors
    else -> throw IllegalStateException()
  }
}

object Game {

  var leftScores = 0L; private set

  var rightScores = 0L; private set

  fun game(left: Figure, right: Figure) {
    leftScores += left.scores
    leftScores += left.roundWith(right).scores
    rightScores += right.scores
    rightScores += right.roundWith(left).scores
  }
}

fun tryBruteforceGame(other: Figure, needScores: Long): Figure {
  for (me in setOf(Rock, Paper, Scissors)) {
    if (me.roundWith(other).scores == needScores) {
      return me
    }
  }
  throw IllegalStateException()
}

fun tryWin(other: Figure): Figure {
  return tryBruteforceGame(other, Win.scores)
}

fun tryLose(other: Figure): Figure {
  return tryBruteforceGame(other, Lose.scores)
}

fun tryDraw(other: Figure): Figure {
  return tryBruteforceGame(other, Draw.scores)
}

fun selectRightFigure(left: Figure, prompt: String): Figure {
  return when (prompt) {
    "X" -> tryLose(left)
    "Y" -> tryDraw(left)
    "Z" -> tryWin(left)
    else -> throw IllegalStateException()
  }
}

fun process(line: String) {
  val (leftMarker, rightPrompt) = line.split(' ')
  val left = parseFigure(leftMarker)
  val right = selectRightFigure(left, rightPrompt)
  game(left, right)
}

fun solution(): Long {
  return rightScores
}

fun main() {
  ::main
    .javaClass
    .getResourceAsStream("/input")!!
    .bufferedReader()
    .forEachLine(::process)
  println(solution())
}