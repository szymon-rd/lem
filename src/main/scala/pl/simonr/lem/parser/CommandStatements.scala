package pl.simonr.lem.parser

import fastparse.{P, *}
import Parser.*

object CommandStatements {

  // run Regulator(ps, ...) on System(ps, ...)
  def runStatement[$: P]: P[Ast.CommandStatement] = P("run" ~/ expression ~ "on" ~ expression).map(Ast.RunStatement.apply.tupled)

  def commandStatement[$: P]: P[Ast.CommandStatement] = runStatement
}