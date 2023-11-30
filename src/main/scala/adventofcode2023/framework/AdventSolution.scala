package space.astrionic
package adventofcode2023.framework

import scala.Console.{GREEN, RED, RESET}

/**
 * Handles reading input from and writing output to files for Advent of Code tasks. The extending class's name needs to
 * contain two digits representing the day.
 */
trait AdventSolution {

  /**
   * Day number with leading zeroes.
   */
  private val day: AdventDay = "\\d\\d".r.findAllIn(this.getClass.getSimpleName).toList match {
    case x :: _ =>
      x.toIntOption match {
        case Some(n) => AdventDay(n)
        case None    => AdventDay(0)
      }
    case Nil => AdventDay(0)
  }

  /**
   * File name for the first part
   */
  private val fileNamePart1: String = s"${day.dayWithLeadingZero}${AdventConfig.partOneSuffix}"

  /**
   * File name for the second part
   */
  private val fileNamePart2: String = s"${day.dayWithLeadingZero}${AdventConfig.partTwoSuffix}"

  /**
   * Controls which parts of the task are executed. Change this value in subclasses to temporarily limit execution to
   * just one of the parts.
   */
  protected[this] var executePart: ExecutePart.Value = ExecutePart.Both

  /**
   * Controls whether the solution will be written to a file. If there is a solution file already it will be
   * overwritten.
   */
  protected[this] var writeSolution = false

  /**
   * Solves both parts by calling [[solvePart1]] and [[solvePart2]] and writes the solutions to output files.
   *
   * @param args
   *   Array of command line arguments for the application
   */
  def main(args: Array[String]): Unit = {
    AdventIO.readInput(day.dayWithLeadingZero) match {
      case Some(input) => executeParts(input)
      case None        => println(s"$RED❌ ERROR: Could not read input.$RESET")
    }
  }

  /**
   * Execute both parts of a puzzle
   * @param input
   *   Puzzle input
   */
  private def executeParts(input: String): Unit = {
    println

    if(executePart == ExecutePart.One || executePart == ExecutePart.Both) {
      execute(input, solvePart1, fileNamePart1, 1)
    }
    if(executePart == ExecutePart.Two || executePart == ExecutePart.Both) {
      execute(input, solvePart2, fileNamePart2, 2)
    }
  }

  /**
   * Compute the solution, time it, check it against the saved solution if it exists or write it to the solution file if
   * [[writeSolution]] is true.
   * @param input
   *   Puzzle input
   * @param solve
   *   The function that computes the solution
   * @param fileName
   *   File name to write to
   * @param partNumber
   *   Number of the part (1 or 2)
   */
  private def execute(input: String, solve: String => String, fileName: String, partNumber: Int): Unit = {
    val startTime = System.nanoTime()
    val solution = solve(input)
    val endTime = System.nanoTime()
    val executionTimeMilliseconds = (endTime - startTime) / 1000000.0

    println(
      f"⚙️ Solution for day ${day.dayWithLeadingZero}, part $partNumber executed in $executionTimeMilliseconds%.1f ms:"
    )
    println(s"$solution\n")

    if(writeSolution) {
      AdventIO.writeSolution(fileName, solution)
      println(s"$GREEN✅ Solution written to file.$RESET")
    } else {
      AdventIO.readSolution(fileName) match {
        case None     =>
        case Some("") =>
        case Some(expected) =>
          if(solution == expected) {
            println(s"$GREEN✅ Correct solution.$RESET")
          } else {
            println(s"$RED❌ ERROR: Does not match the saved solution. The expected solution is:\n$expected\n$RESET")
          }
      }
    }
    println
  }

  /**
   * Should calculate the solution for part 1.
   *
   * @param input
   *   Puzzle input
   * @return
   *   Solution for part 1
   */
  def solvePart1(input: String): String

  /**
   * Should calculate the solution for part 2.
   *
   * @param input
   *   Puzzle input
   * @return
   *   Solution for part 2
   */
  def solvePart2(input: String): String

  /**
   * Used to define which parts of the solution are executed.
   */
  protected[this] object ExecutePart extends Enumeration {
    type ExecutePart = Value
    val One, Two, Both = Value
  }

}
