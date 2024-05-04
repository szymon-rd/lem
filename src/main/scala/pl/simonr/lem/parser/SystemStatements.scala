package pl.simonr.lem.parser

import fastparse.*
import Parser.*

class SystemStatements(nesting: Int) extends Statements(nesting) {

  def inStatement[$: P]: P[Ast.VarStatement] = P("var" ~/ valueDef).map(Ast.VarStatement.apply)
  def behaviourStatement[$: P]: P[Ast.BehaviourStatement] =
    P("behaviour" ~/ identifier ~~ correlate.? ~ assignment).map(Ast.BehaviourStatement.apply.tupled)
  def correlate[$: P]: P[Ast.Correlate] =
    P("(" ~~ correlationType ~~ expression ~~ ")").map(Ast.Correlate.apply.tupled)
  def correlationType[$: P]: P[Ast.CorrelationType] =
    P(("+" | "-").!).map {
      case "+" => Ast.CorrelationType.Positive
      case "-" => Ast.CorrelationType.Negative
    }

  def systemStatement[$: P]: P[Ast.SystemStatement] = P(inStatement | behaviourStatement)
  def systemStatementIndented[$: P]: P[Ast.SystemStatement] = P(spaceIndents ~~ systemStatement)
  def systemStatements[$: P]: P[Ast.SystemStatementList] = P((systemStatementIndented ~~ newline).repX)
    .map { case statements => Ast.SystemStatementList(statements.toList) }


}
