package de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl

import com.google.inject.name.Names
import com.google.inject.{Guice, Inject, Injector}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.reversi.ReversiModule
import de.htwg.se.reversi.controller.controllerComponent.GameStatus._
import de.htwg.se.reversi.controller.controllerComponent._
import de.htwg.se.reversi.model.fileIoComponent.FileIOInterface
import de.htwg.se.reversi.model.gridComponent.{CellInterface, GridInterface}
import de.htwg.se.reversi.model.playerComponent.Player

import scala.swing.Publisher
import scala.util.Random

class Controller @Inject() (var grid: GridInterface) extends ControllerInterface with Publisher {

  var gameStatus: GameStatus = IDLE
  val injector: Injector = Guice.createInjector(new ReversiModule)
  val fileIo: FileIOInterface = injector.instance[FileIOInterface]

  val player1 = Player(1)
  val player2 = Player(2)
  var activePlayer: Int = randomActivePlayer()
  var botPlayer = false


  def getActivePlayer(): Int = activePlayer
  def randomActivePlayer(): Int = Random.shuffle(List(player1.playerId,player2.playerId)).head
  def changePlayer(): Unit = if(activePlayer == 1) activePlayer = player2.playerId else activePlayer = player1.playerId

  def enableBot(): Unit = {
    botPlayer = true
    gameStatus = BOT_ENABLE
    publish(new BotStatus)
  }
  def disableBot(): Unit = {
    botPlayer = false
    gameStatus = BOT_DISABLE
    publish(new BotStatus)
  }
  def botState(): Boolean = botPlayer

  def createEmptyGrid(): Unit = {
    grid.size match {
      case 1 => grid = injector.instance[GridInterface](Names.named("tiny"))
      case 4 => grid = injector.instance[GridInterface](Names.named("small"))
      case 8 => grid = injector.instance[GridInterface](Names.named("normal"))
      case _ =>
    }
    publish(new CellChanged)
  }

  def resize(newSize: Int): Unit = {
    newSize match {
      case 1 => grid = injector.instance[GridInterface](Names.named("tiny"))
      case 4 => grid = injector.instance[GridInterface](Names.named("small"))
      case 8 => grid = injector.instance[GridInterface](Names.named("normal"))
      case _ =>
    }
    activePlayer = randomActivePlayer()
    grid = grid.createNewGrid.highlight(getActivePlayer())
    gameStatus = RESIZE
    publish(GridSizeChanged(newSize))
  }

  override def createNewGrid(): Unit = {
    grid.size match {
      case 1 => grid = injector.instance[GridInterface](Names.named("tiny"))
      case 4 => grid = injector.instance[GridInterface](Names.named("small"))
      case 8 => grid = injector.instance[GridInterface](Names.named("normal"))
      case _ =>
    }
    activePlayer = randomActivePlayer()
    grid = grid.createNewGrid.highlight(getActivePlayer())
    gameStatus = NEW
    publish(new CellChanged)
  }

  def evaluateGame(): Int = grid.evaluateGame()
  override def score(): (Int, Int) = grid.score()
  def finish(): Unit = {
    if(grid.finish(getActivePlayer())) {
      gameStatus = FINISHED
      publish(new Finished)
    }
  }

  def set(row: Int, col: Int, playerId: Int): Unit = {
    val tuple = grid.checkChange(playerId,row,col)
    if(tuple._1) {
      if (playerId == 1) {
        grid = tuple._2.highlight(2)
        gameStatus = SET_Player1
      } else {
        grid = tuple._2.highlight(1)
        gameStatus = SET_Player2
      }
      changePlayer()
      publish(new CellChanged)
    }
  }

  def bot(): Unit = {
    if(activePlayer == 2) {
      if(grid.checkChange(grid.makeNextTurnBot(activePlayer))) {
        Thread.sleep(500)
        grid = grid.makeNextTurnBot(activePlayer).highlight(1)
        changePlayer()
        gameStatus = SET_Bot
        publish(new CellChanged)
      }
    }
  }

  def save(): Unit = {
    fileIo.save(grid)
    fileIo.savePlayer(activePlayer)
    gameStatus = SAVED
    publish(new CellChanged)
  }

  def load(): Unit = {
    val gridOption = fileIo.load
    gridOption match {
      case None =>
        createEmptyGrid()
        gameStatus = COULDNOTLOAD
      case Some(_grid) =>
        grid = _grid
        gameStatus = LOADED
    }
    activePlayer = fileIo.loadPlayer
    publish(new CellChanged)
  }

  def cell(row: Int, col: Int): CellInterface = grid.cell(row, col)
  def gridSize: Int = grid.size
  def statusText: String = GameStatus.message(gameStatus)
  def gridToString: String = grid.toString
}
