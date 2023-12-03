package space.astrionic
package adventofcode2023.framework

import java.util.Calendar
import scala.Console.{RED, RESET}

/**
 * Generates code and input files for a given day.
 */
@main def generateDay(): Unit = readAdventDayFromUser() match {
  case Some(day) =>
    println(s"Generating files for day ${day}.")
    generate(day)
    println("Files generated.")
  case None => println(s"$RED❌ ERROR: Couldn't parse integer.$RESET")
}

/**
 * Lets the user enter an integer.
 * @return
 *   Parsed [[AdventDay]], or [[None]] if the input couldn't be parsed. If the user enters nothing, then the current
 *   date is used.
 */
private def readAdventDayFromUser(): Option[AdventDay] = {
  val default = today()

  print(s"Enter a day (1 to 25, default: $default): ")
  val input: String = io.StdIn.readLine()
  input match {
    case ""    => toAdventDayOption(default)
    case other => toAdventDayOption(other)
  }
}

/**
 * @return
 *   The day number of today's date.
 */
private def today(): Int = {
  val now = Calendar.getInstance()
  val currentDay = now.get(Calendar.DAY_OF_MONTH)
  currentDay
}

private def generate(day: AdventDay): Unit = {
  val codeFile = generateSolutionCodeFile(day)
  writeSolutionCode(day, codeFile)
  createEmptyInputFile(day)
}

private def generateSolutionCodeFile(day: AdventDay): String = readCodeTemplate() match {
  case Some(template) => template.replace(Config.templateDayPlaceholder, day)
  case None =>
    println(s"$RED❌ ERROR: Couldn't read code template.")
    ""
}
