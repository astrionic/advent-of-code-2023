package space.astrionic
package adventofcode2023.framework

type AdventDay = "01" | "02" | "03" | "04" | "05" | "06" | "07" | "08" | "09" | "10" | "11" | "12" | "13" | "14" |
  "15" | "16" | "17" | "18" | "19" | "20" | "21" | "22" | "23" | "24" | "25"

/**
 * Attempts to create an [[AdventDay]] from the given string. Adds a leading zero if necessary.
 */
def toAdventDayOption(s: String): Option[AdventDay] = padLeadingZeroes(s, 2) match {
  case day: AdventDay => Some(day)
  case _              => None
}

/**
 * Attempts to create an [[AdventDay]] from the given string. Adds a leading zero if necessary.
 */
def toAdventDayOption(n: Int): Option[AdventDay] = toAdventDayOption(n.toString)

/**
 * Adds leading zeroes to the front of a string. Returns the string unchanged if its length is longer than or equal to
 * [[targetLength]].
 * @param s
 *   The string to pad
 * @param targetLength
 *   Target string length
 * @return
 *   Padded string
 */
private def padLeadingZeroes(s: String, targetLength: Int): String = {
  ("0" * (targetLength - s.length)) ++ s
}
