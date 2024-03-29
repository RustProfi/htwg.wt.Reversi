package de.htwg.se.reversi.aview

import java.io.File

import de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.reversi.model.fileIoComponent._
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.{Cell, Grid, Matrix}
import org.scalatest.{Matchers, WordSpec}

class TuiSpec extends WordSpec with Matchers {

  "A Go Tui" should {
    val p1 = Grid(new Matrix[Cell](Vector(
      Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
      Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
      Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(3), Cell(0), Cell(0), Cell(0)),
      Vector(Cell(0), Cell(0), Cell(0), Cell(1), Cell(2), Cell(3), Cell(0), Cell(0)),
      Vector(Cell(0), Cell(0), Cell(3), Cell(2), Cell(1), Cell(0), Cell(0), Cell(0)),
      Vector(Cell(0), Cell(0), Cell(0), Cell(3), Cell(0), Cell(0), Cell(0), Cell(0)),
      Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
      Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)))))
    val p2 = Grid(new Matrix[Cell](Vector(
      Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
      Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
      Vector(Cell(0), Cell(0), Cell(0), Cell(3), Cell(0), Cell(0), Cell(0), Cell(0)),
      Vector(Cell(0), Cell(0), Cell(3), Cell(1), Cell(2), Cell(0), Cell(0), Cell(0)),
      Vector(Cell(0), Cell(0), Cell(0), Cell(2), Cell(1), Cell(3), Cell(0), Cell(0)),
      Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(3), Cell(0), Cell(0), Cell(0)),
      Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
      Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)))))
    var controller = new Controller(new Grid(8))
    val tui = new Tui(controller)
    "be able to make en empty grid on input q" in {
      tui.processInputLine("q")
      controller.grid should be(new Grid(8))
    }
    "make a standart reversi field on n" in {

      tui.processInputLine("n")
      if (controller.activePlayer == 1) {
        controller.grid should be(p1)
      } else {
        controller.grid should be(p2)
      }
    }
    "resize to size 1 on ." in {
      tui.processInputLine(".")
      controller.grid should be(new Grid(1))
    }
    "resize to size 4 on +" in {
      val p1small = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(0), Cell(3), Cell(0)),
        Vector(Cell(0), Cell(1), Cell(2), Cell(3)),
        Vector(Cell(3), Cell(2), Cell(1), Cell(0)),
        Vector(Cell(0), Cell(3), Cell(0), Cell(0)))))
      val p2small = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(3), Cell(0), Cell(0)),
        Vector(Cell(3), Cell(1), Cell(2), Cell(0)),
        Vector(Cell(0), Cell(2), Cell(1), Cell(3)),
        Vector(Cell(0), Cell(0), Cell(3), Cell(0)))))
      tui.processInputLine("+")
      if (controller.activePlayer == 1) {
        controller.grid should be(p1small)
      } else {
        controller.grid should be(p2small)
      }

    }
    "resize back to normal on #" in {
      tui.processInputLine("#")
      if (controller.activePlayer == 1) {
        controller.grid should be(p1)
      } else {
        controller.grid should be(p2)
      }
    }
    "enable bot on e" in {
      tui.processInputLine("e")
      controller.botState() should be(true)
    }
    "disable bot on d" in {
      tui.processInputLine("d")
      controller.botState() should be(false)
    }
    "finish when active player has no turns" in {
      val black = Grid(new Matrix[Cell](Vector(Vector(Cell(2), Cell(2)), Vector(Cell(0), Cell(1)))))
      controller.grid = black
      controller.finish()
      val white = Grid(new Matrix[Cell](Vector(Vector(Cell(1), Cell(1)), Vector(Cell(0), Cell(2)))))
      controller.grid = white
      controller.finish()
      val draw = Grid(new Matrix[Cell](Vector(Vector(Cell(2), Cell(2)), Vector(Cell(1), Cell(1)))))
      controller.grid = draw
      controller.finish()
    }
    "undo and redo moves on z an y" in {
      if (controller.getActivePlayer() == 2) controller.changePlayer()
      if (controller.getActivePlayer() == 1) {
        val p1before = Grid(new Matrix[Cell](Vector(
          Vector(Cell(0), Cell(0), Cell(3), Cell(0)),
          Vector(Cell(0), Cell(1), Cell(2), Cell(3)),
          Vector(Cell(3), Cell(2), Cell(1), Cell(0)),
          Vector(Cell(0), Cell(3), Cell(0), Cell(0)))))
        val p1after = Grid(new Matrix[Cell](Vector(
          Vector(Cell(0), Cell(3), Cell(1), Cell(3)),
          Vector(Cell(0), Cell(1), Cell(1), Cell(0)),
          Vector(Cell(0), Cell(2), Cell(1), Cell(3)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0)))))
        controller.grid = p1before
        tui.processInputLine("02")
        controller.grid should be(p1after)
        tui.processInputLine("z")
        controller.grid should be(p1before)
        tui.processInputLine("y")
        controller.grid should be(p1after)
      }
    }
    "bot test" in {
      val p1b = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(0), Cell(3), Cell(0)),
        Vector(Cell(0), Cell(1), Cell(2), Cell(3)),
        Vector(Cell(3), Cell(2), Cell(1), Cell(0)),
        Vector(Cell(0), Cell(3), Cell(0), Cell(0)))))
      controller.grid = p1b
      val plb1 = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(0), Cell(1), Cell(2)),
        Vector(Cell(0), Cell(1), Cell(2), Cell(3)),
        Vector(Cell(3), Cell(2), Cell(1), Cell(0)),
        Vector(Cell(0), Cell(3), Cell(0), Cell(0)))))
      if (controller.getActivePlayer() == 2) controller.changePlayer()
      tui.processInputLine("e")
      tui.processInputLine("02")
      controller.grid should be(plb1)
      tui.processInputLine("z")
      controller.grid should be(p1b)
      tui.processInputLine("y")
      tui.processInputLine("y")
      controller.grid should be(plb1)
      tui.processInputLine("d")
    }
    "set as player 2" in {
      if (controller.getActivePlayer() == 1) controller.changePlayer()
      val p1b = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(3), Cell(0), Cell(0)),
        Vector(Cell(3), Cell(1), Cell(2), Cell(0)),
        Vector(Cell(0), Cell(2), Cell(1), Cell(3)),
        Vector(Cell(0), Cell(0), Cell(3), Cell(0)))))
      controller.grid = p1b
      tui.processInputLine("01")
      controller.grid should be(Grid(new Matrix[Cell](Vector(
        Vector(Cell(3), Cell(2), Cell(3), Cell(0)),
        Vector(Cell(0), Cell(2), Cell(2), Cell(0)),
        Vector(Cell(3), Cell(2), Cell(1), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0))))))
      tui.processInputLine("z")
      tui.processInputLine("y")
      controller.grid should be(Grid(new Matrix[Cell](Vector(
        Vector(Cell(3), Cell(2), Cell(3), Cell(0)),
        Vector(Cell(0), Cell(2), Cell(2), Cell(0)),
        Vector(Cell(3), Cell(2), Cell(1), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0))))))

    }
    "save and load 8x8 json" in {
      controller.grid = new Grid(8)
      controller.fileIo = new fileIoJsonImpl.FileIO
      tui.processInputLine("f")
      var gridjson = new File(System.getProperty("user.dir") + "/grid.json")
      var playerjson = new File(System.getProperty("user.dir") + "/player.json")
      gridjson should exist
      playerjson should exist
      tui.processInputLine("l")
      controller.grid should be(new Grid(8))
      gridjson.delete()
      playerjson.delete()
    }
    "save and load 8x8 xml" in {
      controller.grid = new Grid(8)
      controller.fileIo = new fileIoXmlImpl.FileIO
      tui.processInputLine("f")
      var gridxml = new File(System.getProperty("user.dir") + "/grid.xml")
      var playerxml = new File(System.getProperty("user.dir") + "/player.xml")
      gridxml should exist
      playerxml should exist
      tui.processInputLine("l")
      controller.grid should be(new Grid(8))
      gridxml.delete()
      playerxml.delete()
    }
  }

}
