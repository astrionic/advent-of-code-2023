package space.astrionic
package adventofcode2023.framework

/**
 * Identifies a day in the advent calendar
 *
 * @param dayNumber
 *   The day in the advent calendar. Guaranteed to be a number in the Range 0 to 25.
 * @param dayWithLeadingZero
 *   The day in the advent calendar, as a two-digit String. A leading zero is inserted for days with only one digit.
 */
class AdventDay private (val dayNumber: Int, val dayWithLeadingZero: String)

object AdventDay {

  /**
   * Creates a new [[AdventDay]] with the given day.
   * @param day
   *   The number of the day. Should be in the Range 0 to 25. Values outside this range will be set to 0 instead.
   * @return
   *   A new [[AdventDay]]
   */
  def apply(day: Int): AdventDay = {
    val dayNumber: Int = if(day.abs > 25) 0 else day.abs
    val dayWitheLeadingZero: String = if(dayNumber > 9) s"$dayNumber" else s"0$dayNumber"
    new AdventDay(dayNumber, dayWitheLeadingZero)
  }
}
