package de.htwg.se.reversi

import de.htwg.se.reversi.model.playerComponent.Player

object Reversi {
  def main(args: Array[String]): Unit = {
    val student = Player("Your Name")
    println("Hello, " + student.name)
  }
}
