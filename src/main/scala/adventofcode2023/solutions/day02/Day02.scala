package space.astrionic
package adventofcode2023.solutions.day02

import adventofcode2023.framework.{AdventSolution, toIntOption}

object Day02 extends AdventSolution {

  writeSolution = false
  executePart = ExecutePart.Both

  override def solvePart1(input: String): String = {
    def exceedsMaxCubes(round: ColourTriple): Boolean = round.red > 12 || round.green > 13 || round.blue > 14

    parseInput(input)
      .filter(game => !game.rounds.exists(exceedsMaxCubes))
      .map(_.id)
      .sum
      .toString
  }

  override def solvePart2(input: String): String = {
    def maxColours(a: ColourTriple, b: ColourTriple): ColourTriple =
      ColourTriple(Math.max(a.red, b.red), Math.max(a.green, b.green), Math.max(a.blue, b.blue))

    parseInput(input)
      .map(_.rounds.foldLeft(ColourTriple(0, 0, 0))(maxColours))
      .map(x => x.red * x.green * x.blue)
      .sum
      .toString
  }

  private case class Game(id: Int, rounds: List[ColourTriple])
  private case class ColourTriple(red: Int, green: Int, blue: Int)

  private def parseInput(input: String): List[Game] = {
    val gamePattern = raw"Game (\d+): (.*)".r
    val colourPattern = raw"(\d+) (red|green|blue)".r

    def parseGame(s: String): List[ColourTriple] = s
      .split(";")
      .map(parseRound)
      .toList

    def parseRound(s: String): ColourTriple = {
      val colours = s
        .split(",")
        .map(_.strip)
        .flatMap {
          case colourPattern(n, colour) => Some((colour, n.toInt))
          case _                        => None
        }
        .toMap

      ColourTriple(
        colours.getOrElse("red", 0),
        colours.getOrElse("green", 0),
        colours.getOrElse("blue", 0)
      )
    }

    input
      .split("\n")
      .flatMap(_ match {
        case gamePattern(id, game) => toIntOption(id).map(Game(_, parseGame(game)))
        case _                     => None
      })
      .toList
  }
}
