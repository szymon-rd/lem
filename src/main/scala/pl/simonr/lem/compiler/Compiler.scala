package pl.simonr.lem.compiler

import cats.data.State

import javax.swing.tree.TreeNode

object Compiler {
  type Transformation[T <: TreeNode] = State[Context, T]
  
  def runQuery[T <: TreeNode, Q <: Query[T]](query: Q): Transformation[T] = {
    ???
  }
}
