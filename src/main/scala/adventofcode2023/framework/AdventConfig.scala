package space.astrionic
package adventofcode2023.framework

private[adventofcode2023] object AdventConfig {

  /**
   * Path to the directory containing the input files.
   */
  val inputDirectory = s"$resourceDirectory/input/"

  /**
   * Path to the directory to which the solutions will be written as text files.
   */
  val solutionDirectory = s"$resourceDirectory/solutions/"

  /**
   * Path to the template that is used to generate boilerplate code for each day
   */
  val solutionTemplatePath = s"$resourceDirectory/solution_code_template.txt"

  /**
   * Placeholder for the day number used in the template. Each occurrence in the template will be replaced by the number
   * of the current day.
   */
  val templateDayPlaceholder = "__DAY_NUMBER__"

  /**
   * Path to the directory in which the directories containing the solution Scala files are located.
   */
  val solutionDirPath = "./src/main/scala/adventofcode2023/solutions/"

  /**
   * Prefix for the directory (and package) that contains the code for a solution. The day number (with leading zeroes)
   * is appended to this.
   */
  val solutionDirNamePrefix = "day"

  /**
   * Suffix used to identify files that are related to part 1.
   */
  val partOneSuffix = "_1"

  /**
   * Suffix used to identify files that are related to part 2.
   */
  val partTwoSuffix = "_2"

  /**
   * Path to the resource directory which contains files like input, solutions and templates.
   */
  private lazy val resourceDirectory = "./src/main/resources"
}
