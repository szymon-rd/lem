package pl.simonr.lem.parser

object Ast {
  sealed trait AstNode {
    def prettyPrint: fansi.Str =
      pprint(this)
  }
  sealed trait TopLevelDef extends AstNode
  case class CompilationUnit(defs: List[TopLevelDef]) extends AstNode

  sealed trait Expression extends AstNode
  case class Identifier(name: String) extends Expression
  sealed trait Constant extends Expression
  case class IntConst(value: Long) extends Constant
  case class ArrayConst(values: List[Expression]) extends Constant
  case class ArrayAccess(array: Expression, index: Expression) extends Expression

  case class FunctionCall(name: Identifier, arguments: List[Expression]) extends Expression

  case class Type(name: Identifier, arraySize: Option[Expression]) extends AstNode
  case class ValueDefinition(name: Identifier, tp: Type) extends AstNode
  case class Parameters(parameters: List[ValueDefinition]) extends AstNode
  
  case class System(name: Identifier, parameters: Option[Parameters], statements: SystemStatementList) extends TopLevelDef
  sealed trait SystemStatement extends AstNode
  case class SystemStatementList(statements: List[SystemStatement])
  case class VarStatement(valueDef: ValueDefinition) extends SystemStatement
  enum CorrelationType {
    case Positive
    case Negative
  }
  case class BehaviourStatement(name: Identifier, correlate: Option[Correlate], behaviour: Expression) extends SystemStatement
  case class Correlate(cType: CorrelationType, correlate: Expression) extends AstNode

  case class Regulator(name: Identifier, parameters: Option[Parameters], system: Expression, statements: RegulatorStatementList) extends TopLevelDef
  sealed trait RegulatorStatement extends AstNode
  case class RegulatorStatementList(statements: List[RegulatorStatement])
  case class RegulateStatement(varName: Identifier, regulator: Expression) extends RegulatorStatement
  case class EquilibriumStatement(expr: Expression) extends RegulatorStatement
  case class RunRegulator(regulator: Expression, system: Expression) extends TopLevelDef
  
  sealed trait CommandStatement extends TopLevelDef
  case class RunStatement(regulator: Expression, system: Expression) extends CommandStatement
  
}
