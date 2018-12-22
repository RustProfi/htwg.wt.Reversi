package de.htwg.se.reversi.aview

//import de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Grid

import org.scalatest.{Matchers, WordSpec}
/*
class TuiSpec extends WordSpec with Matchers {
  "A Reversi Tui" should {
    val tui = new Tui
    "create and empty Sudoku on input 'e'" in {
      val ret = tui.processInputLine("q", new Grid(8))
      ret should be()
      //controller.grid should be(new Grid(9))
    }
  }
}
*/
/*
class Tui {

  def processInputLine(input: String, grid:Grid):Grid = {
    input match {
      case "q" => grid
      case "n"=> new Grid(8)
      //case "r" => new GridCreator(9).createRandom(16)
      /*case "s" =>
        val (success, solvedGrid) = new Solver(grid).solve;
        if (success) println("Puzzle solved")else println("This puzzle could not be solved!")
        solvedGrid*/
      case _ => {
        input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
          case row :: column :: value :: Nil => grid.set(row, column, value)
          case _ => grid
        }
      }
    }
  }
}
*/


