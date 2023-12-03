/**
 * Contains functions to read from and write to files.
 */

package space.astrionic
package adventofcode2023.framework

import java.io.{File, FileNotFoundException, FileWriter}
import scala.io.Source
import scala.util.control.Exception.catching

/**
 * Reads the problem input from the input file
 *
 * @return
 *   Input file contents, or [[None]] if the file can't be found.
 */
def readInput(filename: String): Option[String] =
  readFile(s"${Config.inputDirectory}$filename.txt")

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
  readFile(Config.solutionTemplatePath)

/**
 * Reads the solution from the solution file.
 * @param filename
 *   File name to read from (excluding file extension).
 * @return
 *   The solution for the given puzzle, or [[None]] if the file can't be found.
 */
def readSolutionFromFile(filename: String): Option[String] =
  readFile(s"${Config.solutionDirectory}$filename.txt")

/**
 * Writes a solution to a file.
 * @param filename
 *   File name to write to (excluding file extension).
 * @param content
 *   Contents to write.
 */
def writeSolutionToFile(filename: String, content: String): Unit =
  writeToFile(s"${Config.solutionDirectory}", s"$filename.txt", content)

/**
 * @param directoryPath
 *   Path to the directory that will contain the file to which the content should be written. Will be created if it
 *   doesn't exist.
 * @param filename
 *   Name of the file to write to. Will be created if it doesn't exist.
 * @param content
 *   Content to write.
 * @param append
 *   If [[true]], new content will be appended to existing file If [[false]], the file will be completely overwritten.
 */
def writeToFile(directoryPath: String, filename: String, content: String, append: Boolean = false): Unit = {
  val directory = new File(directoryPath)
  if(!directory.exists()) {
    directory.mkdirs()
  }
  new FileWriter(new File(directoryPath + filename), append) {
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
  val dirPath = s"${Config.solutionDirPath}${Config.solutionDirNamePrefix}$day/"
  val filename = s"Day$day.scala"
  writeToFile(dirPath, filename, code)
}

/**
 * Creates an empty input file for the given day.
 * @param day
 *   Day
 */
def createEmptyInputFile(day: AdventDay): Unit = {
  val filename = s"$day.txt"
  writeToFile(Config.inputDirectory, filename, "")
}
