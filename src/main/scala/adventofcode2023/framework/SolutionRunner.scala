/**
 * Handles reading input from and writing output to files for Advent of Code tasks. The extending class's name needs to
 * contain two digits representing the day.
 */

package space.astrionic
package adventofcode2023.framework

import scala.Console.{GREEN, RED, RESET}

/**
 * File name for the first part
 */
private def fileNamePart1(day: String): String = s"$day${Config.partOneSuffix}"

/**
 * File name for the second part
 */
private def fileNamePart2(day: String): String = s"$day${Config.partTwoSuffix}"

/**
 * Solves both parts by calling [[part1]] and [[part2]] and writes the solutions to output files. Also measures the
 * execution time and compares the output the solution that was previously written to a file if it exists.
 * @param day
 *   The solution's date. Primarily used in filenames for the input and output files
 * @param part1
 *   A function that solves part 1
 * @param part2
 *   A function that solves part 2
 * @param executePart
 *   Controls which part are executed, e.g. to skip execution of part 1 while testing part 2.
 * @param writeToFile
 *   Whether the solution is written to a file. If a solution file exists, it will be overwritten if [[true]]. If
 *   [[false]], new solutions are checked against existing solutions. The result of the comparison is written to the
 *   console.
 */
def executeSolution(
    day: AdventDay,
    part1: String => String,
    part2: String => String,
    executePart: ExecutePart = ExecutePart.Both,
    writeToFile: Boolean = false
): Unit = {
  readInput(day) match {
    case Some(input) => executeParts(day, input, part1, part2, executePart, writeToFile)
    case None        => println(s"$RED❌ ERROR: Could not read input.$RESET")
  }
}

/**
 * Execute both parts of a puzzle
 * @param input
 *   Puzzle input
 */
private def executeParts(
    day: AdventDay,
    input: String,
    part1: String => String,
    part2: String => String,
    executePart: ExecutePart,
    writeToFile: Boolean
): Unit = {
  println

  if(executePart == ExecutePart.One || executePart == ExecutePart.Both) {
    execute(day, input, part1, fileNamePart1(day), 1, writeToFile)
  }
  if(executePart == ExecutePart.Two || executePart == ExecutePart.Both) {
    execute(day, input, part2, fileNamePart2(day), 2, writeToFile)
  }
}

/**
 * Compute the solution, time it, check it against the saved solution if it exists or write it to the solution file if
 * [[writeSolutionToFile]] is true.
 * @param input
 *   Puzzle input
 * @param solve
 *   The function that computes the solution
 * @param fileName
 *   File name to write to
 * @param partNumber
 *   Number of the part (1 or 2)
 * @param writeSolution
 *   If [[true]], the solution is written to a file
 */
private def execute(
    day: AdventDay,
    input: String,
    solve: String => String,
    fileName: String,
    partNumber: PartNumber,
    writeSolution: Boolean
): Unit = {
  val startTime = System.nanoTime()
  val solution = solve(input)
  val endTime = System.nanoTime()
  val executionTimeMilliseconds = (endTime - startTime) / 1000000.0

  println(
    f"⚙️ Solution for day $day, part $partNumber executed in $executionTimeMilliseconds%.1f ms:"
  )
  println(s"$solution\n")

  if(writeSolution) {
    writeSolutionToFile(fileName, solution)
    println(s"$GREEN✅ Solution written to file.$RESET")
  } else {
    readSolutionFromFile(fileName) match {
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
 * Used to define which parts of the solution are executed.
 */
enum ExecutePart {
  case One, Two, Both
}

private type PartNumber = 1 | 2
