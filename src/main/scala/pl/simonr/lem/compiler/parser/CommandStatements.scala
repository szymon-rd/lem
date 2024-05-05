package pl.simonr.lem.compiler.parser

import fastparse.{P, *}
import Parser.*
import pl.simonr.lem.compiler.Ast

object CommandStatements {

  // run Regulator(ps, ...) on System(ps, ...)
  def runStatement[$: P]: P[Ast.CommandStatement] = P("run" ~/ expression ~ "on" ~ expression).map(Ast.RunStatement.apply.tupled)

  def commandStatement[$: P]: P[Ast.CommandStatement] = runStatement
}