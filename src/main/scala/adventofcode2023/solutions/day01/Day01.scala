package space.astrionic
package adventofcode2023.solutions.day01

import adventofcode2023.framework.executeSolution

import scala.util.matching.Regex

@main def day01(): Unit = executeSolution("01", part1, part2)

def part1(input: String): String = {
  val regex = raw"\D*(\d)".r.unanchored

  def firstDigitOf: String => Option[String] = matchRegex(regex)

  def firstAndLastDigitOf(s: String): Option[String] = (firstDigitOf(s), firstDigitOf(s.reverse)) match {
    case (Some(first), Some(last)) => Some(s"${first}${last}")
    case _                         => None
  }

  input
    .split("\n")
    .flatMap(firstAndLastDigitOf)
    .flatMap(_.toIntOption)
    .sum
    .toString
}

def part2(input: String): String = {
  val digitMap = Map(
    "one" -> "1",
    "two" -> "2",
    "three" -> "3",
    "four" -> "4",
    "five" -> "5",
    "six" -> "6",
    "seven" -> "7",
    "eight" -> "8",
    "nine" -> "9"
  )

  val regex = s"(\\d|${digitMap.keys.mkString("|")})".r.unanchored
  def firstDigitOf: String => Option[String] = matchRegex(regex)

  val digitStringsReverse = digitMap.keys.map(_.reverse).mkString("|")
  val regexReverse = s"(\\d|${digitStringsReverse})".r.unanchored
  def lastDigitOf(s: String): Option[String] = matchRegex(regexReverse)(s.reverse).map(_.reverse)

  def wordToDigit(s: String): String = {
    digitMap.get(s) match {
      case Some(digit) => digit
      case None        => s
    }
  }

  def firstAndLastDigitOf(s: String): Option[String] = (firstDigitOf(s), lastDigitOf(s)) match {
    case (Some(first), Some(last)) => Some(s"${wordToDigit(first)}${wordToDigit(last)}")
    case _                         => None
  }

  input
    .split("\n")
    .flatMap(firstAndLastDigitOf)
    .flatMap(_.toIntOption)
    .sum
    .toString
}

private def matchRegex(pattern: Regex)(s: String): Option[String] = s match {
  case pattern(group) => Some(group)
  case _              => None
}
