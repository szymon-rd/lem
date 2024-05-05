package pl.simonr.lem.compiler

import java.util.concurrent.atomic.AtomicLong

object NodeIds {
  opaque type TreeId = Long
  
  private val id: AtomicLong = new AtomicLong(0)

  def nextId(): TreeId = id.incrementAndGet()
}
