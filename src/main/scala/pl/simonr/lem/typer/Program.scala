package pl.simonr.lem.typer

object Program {
  sealed trait Node {
    def prettyPrint: fansi.Str = pprint(this)
  }

  case class Program(
    systems: List[System],
    regulators: List[Regulator],
    commands: List[Command]
  ) extends Node
  
  case class System(name: String) extends Node
  case class Regulator(name: String) extends Node

  sealed trait Command
}
