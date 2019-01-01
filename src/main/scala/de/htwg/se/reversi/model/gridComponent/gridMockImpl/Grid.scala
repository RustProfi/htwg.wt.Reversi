package de.htwg.se.reversi.model.gridComponent.gridMockImpl

import de.htwg.se.reversi.model.gridComponent.{CellInterface, GridInterface, gridBaseImpl}
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Turn

class Grid(var size:Int) extends GridInterface{

  size=1
  def cell(row: Int, col: Int): CellInterface = EmptyCell
  def set(row: Int, col: Int, value: Int): GridInterface = this
  def setTurnRC(playerId: Int, row: Int, col: Int): GridInterface = this
  def reset(row:Int, col:Int): GridInterface = this
  def createNewGrid: GridInterface = this
  def evaluateGame():Int = 0
  def highlight(playerId: Int): GridInterface = this
  def finish(activePlayer: Int): Boolean = false

  override def score(): (Int, Int) = (0,0)

  override def checkChange(gridnew: GridInterface): Boolean = ???

  override def unhighlight(): gridBaseImpl.Grid = ???

  override def getValidTurns(playerId: Int): List[Turn] = ???

  override def setTurn(turn: Turn, value: Int): gridBaseImpl.Grid = ???

  override def makeNextTurnKI(playerId: Int): gridBaseImpl.Grid = ???

  override def makeNextTurnRandom(playerId: Int): gridBaseImpl.Grid = ???
}

object EmptyCell extends CellInterface {
  def value: Int = 0
  def isSet: Boolean = false
}