package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

case class Matrix[T](rows: Vector[Vector[T]]) {
  val size: Int = rows.size

  def this(size: Int, filling: T) = this(Vector.tabulate(size, size) { (row, col) => filling })

  def cell(row: Int, col: Int): T = rows(row)(col)

  def fill(filling: T): Matrix[T] = copy(Vector.tabulate(size, size) { (row, col) => filling })

  def replaceCell(row: Int, col: Int, cell: T): Matrix[T] = copy(rows.updated(row, rows(row).updated(col, cell)))
}
