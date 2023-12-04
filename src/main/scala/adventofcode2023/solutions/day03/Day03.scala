package space.astrionic
package adventofcode2023.solutions.day03

import adventofcode2023.framework.executeSolution

@main def day03(): Unit = executeSolution("03", part1, part2)

private def part1(input: String): String = {
  val lines = input.split("\n")

  val isAdjacentToSymbol: PuzzleNumber => Boolean = (num: PuzzleNumber) => {
    val start = Math.max(0, num.start - 1)
    val end = Math.min(lines(num.lineIndex).length, num.end + 1)
    val lineBefore = num.lineIndex - 1
    val lineAfter = num.lineIndex + 1

    val getSymbolsOnLine = (lineIndex: Int) =>
      lines
        .lift(lineIndex)
        .map(_.substring(start, end))
        .getOrElse("")
        .toCharArray
        .filter(c => !c.isDigit && c != '.')

    (lineBefore to lineAfter)
      .flatMap(getSymbolsOnLine(_))
      .nonEmpty
  }

  lines.zipWithIndex
    .flatMap(lineToPuzzleNumberList)
    .filter(isAdjacentToSymbol)
    .map(_.value)
    .sum
    .toString
}

private def part2(input: String): String = {
  val lines = input.split("\n")
  val gearRegex = raw"(\*)".r.unanchored

  val gears = lines.zipWithIndex
    .flatMap((line, idx) => gearRegex.findAllMatchIn(line).map(m => Gear(idx, m.start)))

  val numbers = lines.zipWithIndex
    .flatMap(lineToPuzzleNumberList)

  gears
    .flatMap(g => {
      numbers.filter(areAdjacent(g, _)).toList match {
        case one :: two :: Nil => Some(one.value * two.value)
        case _                 => None
      }
    })
    .sum
    .toString
}

private case class PuzzleNumber(value: Int, lineIndex: Int, start: Int, end: Int)
private case class Gear(lineIndex: Int, rowIndex: Int)

private def lineToPuzzleNumberList(line: String, idx: Int): List[PuzzleNumber] = {
  val numberRegex = raw"(\d+)".r.unanchored

  numberRegex
    .findAllMatchIn(line)
    .flatMap(m => m.matched.toIntOption.map(num => PuzzleNumber(num, idx, m.start, m.end)))
    .toList
}

private def areAdjacent(gear: Gear, num: PuzzleNumber): Boolean = {
  // Because all numbers in the input have exactly 3 digits, they have to start or end in this window
  val adjacentLines = gear.lineIndex - 1 to gear.lineIndex + 1
  val adjacentRows = gear.rowIndex - 1 to gear.rowIndex + 1

  adjacentLines.contains(num.lineIndex) && (adjacentRows.contains(num.start) || adjacentRows.contains(num.end - 1))
}
