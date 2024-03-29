package de.htwg.se.reversi.model.playerComponent

import org.scalatest._

class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player(1)
      "have a name" in {
        player.playerId should be(1)
      }
    }
  }
}
