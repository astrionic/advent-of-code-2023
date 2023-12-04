package space.astrionic
package adventofcode2023.solutions.day04

import adventofcode2023.framework.executeSolution

import scala.math.pow

@main def day04(): Unit = executeSolution("04", part1, part2)

private def part1(input: String): String = {
  parseInput(input)
    .map(_.scorePart1)
    .sum
    .toString
}

private def part2(input: String): String = {
  val cards = parseInput(input)
  val numMatchesById: Map[Int, Int] = cards.map(card => (card.id, card.scorePart2)).toMap
  var scoreCache: Map[Int, Int] = Map()

  def scoreFor(id: Int): Int = scoreCache.get(id) match {
    case Some(score) => score
    case None =>
      numMatchesById.get(id) match {
        case None    => 0 // According to the description this isn't supposed to happen for the given input
        case Some(0) => 1
        case Some(n) =>
          val score = 1 + ((id + 1) to (id + n)).map(scoreFor).sum
          scoreCache = scoreCache + (id -> score)
          score
      }
  }

  cards.map(card => scoreFor(card.id)).sum.toString
}

private def parseInput(input: String): List[Card] = {
  val regex = raw"Card\s+(\d+):((?:\s*\d+)+) \|((?:\s*\d+)+)".r

  def toIntSet(s: String): Set[Int] = s
    .split(raw"\s+")
    .flatMap(_.toIntOption)
    .toSet

  input
    .split("\n")
    .flatMap {
      case regex(id, winningNumbers, myNumbers) => Some(Card(id.toInt, toIntSet(winningNumbers), toIntSet(myNumbers)))
      case _                                    => None
    }
    .toList
}

private case class Card(id: Int, winningNumbers: Set[Int], myNumbers: Set[Int]) {
  private def numMatches: Int = winningNumbers.intersect(myNumbers).size

  def scorePart1: Int = {
    if(numMatches > 0)
      pow(2, numMatches - 1).intValue
    else
      0
  }

  def scorePart2: Int = numMatches
}
