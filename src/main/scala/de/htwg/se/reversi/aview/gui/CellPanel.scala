package de.htwg.se.reversi.aview.gui

import scala.swing._
import scala.swing.event._

import de.htwg.se.reversi.controller.controllerComponent.{ CellChanged, ControllerInterface, Finished, BotStatus}

class CellPanel(row: Int, column: Int, controller: ControllerInterface) extends FlowPanel {

  val cellColor = new Color(224, 224, 255)
  val highlightedCellColor = new Color(192, 255, 192)
  val unhighlightedCellColor = new Color(255, 189, 189)

  def myCell = controller.cell(row, column)

  def cellText(row: Int, col: Int) = if(controller.cell(row,column).value == 3)  "" else  "" + controller.cell(row,column)

  val label =
    new Label {
      text = cellText(row, column)
      font = new Font("Verdana", 1, 36)
    }

  val cell = new BoxPanel(Orientation.Vertical) {
    contents += label
    preferredSize = new Dimension(50, 50)
    background = cellColor
    //border = Swing.BeveledBorder(Swing.Raised)
    listenTo(mouse.clicks)
    listenTo(mouse.moves)
    listenTo(controller)
    reactions += {
      case e: CellChanged => {
        label.text = cellText(row, column)
        repaint
      }
      case MouseClicked(src, pt, mod, clicks, pops) => {
        controller.set(row,column,controller.getActivePlayer())
        if(controller.botstate()) controller.bot
        controller.finish
        repaint
      }
      case MouseEntered(src,pt,mod) => {
        if (controller.cell(row,column).value == 3) {background = highlightedCellColor }
        else if (controller.cell(row,column).isSet) {background = cellColor }
        else {background = unhighlightedCellColor}
        repaint
      }
      case MouseExited(src,pt,mod) => {
        background = cellColor
        repaint
      }
    }
  }

  def redraw = {
    contents.clear()
    label.text = cellText(row, column)
    setBackground(cell)
    contents += cell
    repaint
  }

  def setBackground(p: Panel) = p.background = cellColor

}