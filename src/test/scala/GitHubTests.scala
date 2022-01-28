package lili.hubs.tests

import org.scalatest._
import flatspec._
import matchers._
import org.scalatest.matchers.should.Matchers

import lili.hubs.github.GitHub

class GitHubTests extends AnyFlatSpec with Matchers:
   val gitHub = new GitHub()

   "'amuradyan'-s name on GitHub" should "be missing" in {
      gitHub.getUserName("amuradyan") shouldEqual None
   }

   "'jdegoes'-s name on GitHub" should "be John A. De Goes" in {
      gitHub.getUserName("jdegoes") shouldEqual Some("John A. De Goes")
   }

// dekanat has 4 public repos
// haniravi has 9 public repos
