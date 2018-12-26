package de.htwg.se.reversi.aview

//import com.typesafe.scalalogging.{LazyLogging, Logger}
//import de.htwg.se.sudoku.controller.controllerComponent.ControllerInterface
//import de.htwg.se.sudoku.controller.controllerComponent.GameStatus
//import de.htwg.se.sudoku.controller.controllerComponent.{CandidatesChanged, CellChanged, GridSizeChanged}
import de.htwg.se.reversi.model.playerComponent.Player

//import de.htwg.se.reversi.model.{Grid,GridCreator,Solver}
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.GridCreator
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Grid

class Tui {
  val player1 = new Player(1)
  val player2 = new Player(2)
  var activePlayer = player1.playerId
  //var notactivePlayer

  def changePlayer(): Unit = if(activePlayer == 1)  activePlayer = player2.playerId else if (activePlayer == 2) activePlayer = player1.playerId


  def processInputLine(input: String, grid:Grid):Grid = input match {
    case "q" => grid.createNewGrid
    case "n"=> new Grid(8).createNewGrid
    case "h1" => grid.highlight(1)
    case "h2" => grid.highlight(2)
    case _ => input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
      case row :: column :: Nil => {
        var returngrid = grid
        if (grid.checkChange(grid.setTurnRC(activePlayer, row, column))) {
          returngrid = grid.setTurnRC(activePlayer, row, column)
          changePlayer()
        }
        returngrid.highlight(activePlayer)
      }
      case _ => grid
    }
  }
}
