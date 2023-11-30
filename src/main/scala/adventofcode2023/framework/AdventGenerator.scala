package space.astrionic
package adventofcode2023.framework

import java.util.Calendar

import scala.Console.{RED, RESET}

/**
 * Generates code and input files for a given day.
 */
object AdventGenerator {
  def main(args: Array[String]): Unit = run()

  private def run(): Unit = {
    readIntFromUser() match {
      case Some(n) =>
        val day = AdventDay(n)
        println(s"Generating files for day ${day.dayNumber}.")
        generate(day)
        println("Files generated.")
      case None => println(s"$RED❌ ERROR: Couldn't parse integer.$RESET")
    }
  }

  /**
   * Lets the user enter an integer.
   * @return
   *   Parsed [[Int]], or [[None]] if the input couldn't be parsed. If the user enters nothing, then the current date is
   *   used.
   */
  private def readIntFromUser(): Option[Int] = {
    val default = today()
    print(s"Enter a day (0 to 25, default: $default): ")
    val input: String = io.StdIn.readLine()
    input match {
      case ""    => Some(default)
      case other => other.toIntOption
    }
  }

  private def generate(day: AdventDay): Unit = {
    val codeFile = generateSolutionCodeFile(day)
    AdventIO.writeSolutionCode(day, codeFile)
    AdventIO.createEmptyInputFile(day)
  }

  /**
   * Creates the Scala code for a given day
   * @param day
   *   Day for which the file should be generated.
   * @return
   *   String containing the generated boilerplate code
   */
  private def generateSolutionCodeFile(day: AdventDay): String =
    AdventIO.readCodeTemplate() match {
      case Some(template) => template.replace(AdventConfig.templateDayPlaceholder, day.dayWithLeadingZero)
      case None =>
        println(s"$RED❌ ERROR: Couldn't read code template.")
        ""
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
}
