package pl.simonr.lem.parser

import fastparse.*
import pl.simonr.lem.parser.Parser.*

class RegulatorStatements(nesting: Int) extends Statements(nesting) {

  def regulateStatement[$: P]: P[Ast.RegulateStatement] = P("regulate" ~ identifier ~ "by" ~ expression).map(Ast.RegulateStatement.apply)
  def equilibriumStatement[$: P]: P[Ast.EquilibriumStatement] = P("equilibrium" ~ expression).map(Ast.EquilibriumStatement.apply)
  def regulatorStatement[$: P]: P[Ast.RegulatorStatement] = P(regulateStatement | equilibriumStatement)
  def indentedRegulatorStatement[$: P]: P[Ast.RegulatorStatement] = P(spaceIndents ~~ regulatorStatement)
  def regulatorStatements[$: P]: P[Ast.RegulatorStatementList] = P((indentedRegulatorStatement ~~ newline).repX)
    .map(statements => Ast.RegulatorStatementList(statements.toList))


}
