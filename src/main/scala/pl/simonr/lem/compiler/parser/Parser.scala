package pl.simonr.lem.compiler.parser

import fastparse.*
import pl.simonr.lem.compiler.Ast
import pl.simonr.lem.compiler.Ast.*

import java.lang

object Parser {
  val spacesInTab = 2

  def ws[$: P] = P(CharsWhileIn(" "))

  implicit object whitespace extends fastparse.Whitespace {
    def apply(ctx: P[_]): P[Unit] = ws(ctx)
  }

  def space[$: P]   = P(" ")
  def newline[$: P] = P("\n" | "\r\n")
  def defOpen[$: P] = P(":")

  def compilationUnit[$: P]: P[CompilationUnit] = P(topLevelDef.repX.? ~~ newline.repX.? ~~ End).map(xs => Ast.CompilationUnit(xs.toList.flatten))
  def topLevelDef[$: P]: P[TopLevelDef]         = P(newline.repX ~~ (systemDef | regulatorDef | CommandStatements.commandStatement))
  def systemDef[$: P]: P[lang.System] = P("system" ~ identifier ~~ parametersList.? ~~ defOpen ~~ newline ~~ SystemStatements(1).systemStatements)
    .map(Ast.System.apply.tupled)
  def regulatorDef[$: P]: P[Regulator] = P("regulator" ~ identifier ~~ parametersList.? ~ "on" ~ expression ~~ defOpen ~~ newline ~~ RegulatorStatements(1).regulatorStatements)
    .map(Ast.Regulator.apply.tupled)

  def tpe[$: P] = P(identifier ~~ arraySize.?).map(Ast.Type.apply.tupled)

  def valueDef[$: P]: P[ValueDefinition]  = P(identifier ~~ defOpen ~ tpe).map(Ast.ValueDefinition.apply.tupled)
  def parametersList[$: P]: P[Parameters] = P("(" ~~ valueDef.rep(sep = ",") ~~ ")").map(xs => Ast.Parameters(xs.toList))

  def identifier[$: P]: P[Identifier] =
    P( (letter|"_") ~~ (letter | digit | "_").repX ).!.filter(!keywords.contains(_)).map(Ast.Identifier.apply)

  def letter[$: P]     = P( lowercase | uppercase )
  def lowercase[$: P]  = P( CharIn("a-z") )
  def uppercase[$: P]  = P( CharIn("A-Z") )
  def digit[$: P]      = P( CharIn("0-9") )
  def integer[$: P]: P[IntConst]    = P( ("-".? ~~ digit.repX(min = 1).!)).map(i => Ast.IntConst(i.toLong))
  def array[$: P]: P[ArrayConst] = P("[" ~~ expression.rep(sep = ",") ~~ "]").map(xs => ArrayConst(xs.toList))
  def arrayAccess[$: P] = P(identifier ~~ "[" ~~ expression ~~ "]").map(Ast.ArrayAccess.apply.tupled)

  def constant[$: P]: P[Constant] = P( integer | array)

  def functionCall[$: P]: P[FunctionCall] = P(identifier ~~ "(" ~~ expression.rep(sep = ",") ~~ ")").map {
    case (name, args) => Ast.FunctionCall(name, args.toList)
  }

  def expression[$: P]: P[Expression] = P(functionCall | arrayAccess | constant | identifier)

  def arraySize[$: P]: P[Expression] = P("[" ~~ (integer | identifier) ~~ "]")

  def assignment[$: P]: P[Expression] = P("=" ~ expression)

  val keywords = Set(
    "open", "system", "in"
  )
}
