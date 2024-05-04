package pl.simonr.lem

import pl.simonr.lem.parser.Parser
import fastparse.*

@main
def main: Unit =
  val x = parse(
  """ |system Crossroads(n: int):
      |  var cars: int[n]
      |  var openLanes: bool[n]
      |  behaviour carsArriving = grow(cars)
      |  behaviour carsLeaving(+openLanes[n]) = shrink(cars[n])
      |
      |regulator Lights(n: int, stateGroups: int[n]) on Crossroads(n):
      |  regulate openLanes by choose(stateGroups)
      |  equilibrium min(cars)
      |
      |run Lights(3, [[1,1,0,0], [0,0,1,1]]) on Crossroads(4)
      |
      |""".stripMargin, Parser.compilationUnit(_))
  x match
    case s@Parsed.Success(value, _) =>
      println("Success")
      println(value.prettyPrint)
    case f@Parsed.Failure(_, _, extra) =>
      println("Failure: " + f)
      println(extra.trace().longAggregateMsg)




