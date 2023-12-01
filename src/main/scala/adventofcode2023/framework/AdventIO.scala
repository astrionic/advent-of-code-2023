package space.astrionic
package adventofcode2023.framework

import java.io.{File, FileNotFoundException, PrintWriter}
import scala.io.Source
import scala.util.control.Exception.catching

/**
 * Contains functions to read from and write to files.
 */
private[adventofcode2023] object AdventIO {

  /**
   * Reads the problem input from the input file
   *
   * @return
   *   Input file contents, or [[None]] if the file can't be found.
   */
  def readInput(fileName: String): Option[String] =
    readFile(s"${AdventConfig.inputDirectory}$fileName.txt")

  /**
   * Reads files
   * @param path
   *   File path
   * @return
   *   File contents, or None if the file doesn't exist
   */
  private def readFile(path: String): Option[String] =
    catching(classOf[FileNotFoundException]).opt {
      val source = Source.fromFile(path)
      val text = source.getLines().mkString("\n")
      source.close()
      text
    }

  /**
   * Reads the code template from the template file.
   * @return
   *   The code template, or [[None]] if the file can't be found.
   */
  def readCodeTemplate(): Option[String] =
    readFile(AdventConfig.solutionTemplatePath)

  /**
   * Reads the solution from the solution file.
   * @param fileName
   *   File name to read from (excluding file extension).
   * @return
   *   The solution for the given puzzle, or [[None]] if the file can't be found.
   */
  def readSolution(fileName: String): Option[String] =
    readFile(s"${AdventConfig.solutionDirectory}$fileName.txt")

  /**
   * Writes a solution to a file.
   * @param fileName
   *   File name to write to (excluding file extension).
   * @param content
   *   Contents to write.
   */
  def writeSolution(fileName: String, content: String): Unit =
    writeFile(s"${AdventConfig.solutionDirectory}", s"$fileName.txt", content)

  /**
   * @param directoryPath
   *   Path to the directory that will contain the file to which the content should be written. Will be created if it
   *   doesn't exist.
   * @param fileName
   *   Name of the file to write to. Will be created if it doesn't exist. Existing content is overwritten.
   * @param content
   *   Content to write.
   */
  private def writeFile(directoryPath: String, fileName: String, content: String): Unit = {
    val directory = new File(directoryPath)
    if(!directory.exists()) {
      directory.mkdirs()
    }
    new PrintWriter(new File(directoryPath + fileName)) {
      write(content)
      close()
    }
  }

  /**
   * Writes the code for a given day to a Scala file.
   * @param day
   *   Day
   * @param code
   *   Code to write
   */
  def writeSolutionCode(day: AdventDay, code: String): Unit = {
    val dirPath = s"${AdventConfig.solutionDirPath}${AdventConfig.solutionDirNamePrefix}${day.dayWithLeadingZero}/"
    val fileName = s"Day${day.dayWithLeadingZero}.scala"
    writeFile(dirPath, fileName, code)
  }

  /**
   * Creates an empty input file for the given day.
   * @param day
   *   Day
   */
  def createEmptyInputFile(day: AdventDay): Unit = {
    val fileName = s"${day.dayWithLeadingZero}.txt"
    writeFile(AdventConfig.inputDirectory, fileName, "")
  }
}
