package space.astrionic
package adventofcode2023.framework

import scala.util.matching.Regex


def toIntOption(s: String): Option[Int] = util.Try(s.toInt).toOption

def matchRegex(pattern: Regex)(s: String): Option[String] = s match {
  case pattern(group) => Some(group)
  case _ => None
}
