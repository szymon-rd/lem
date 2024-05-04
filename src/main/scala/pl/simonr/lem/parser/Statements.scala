package pl.simonr.lem.parser

import fastparse.*
import pl.simonr.lem.parser.Parser.ws


trait Statements(nesting: Int) {
  implicit object whitespace extends fastparse.Whitespace {
    def apply(ctx: P[_]): P[Unit] = ws(ctx)
  }
  def spaceIndents[$: P] = P(Parser.space.repX(nesting * Parser.spacesInTab))

}
