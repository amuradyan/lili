package lili.hubs.tests

import lili.hubs._

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

   "'dekanat'" should "have the following four repositories" in {
      gitHub.listOrganizationRepositories("dekanat") shouldEqual List(
        HubRepository("dekanat", "darling"),
        HubRepository("dekanat", "kogu"),
        HubRepository("dekanat", "saluto"),
        HubRepository("dekanat", "whiteboard")
      )
   }

   "'haniravi'" should "have the following four repositories" in {
      gitHub.listOrganizationRepositories("haniravi") shouldEqual List(
        HubRepository("haniravi", "prelude"),
        HubRepository("haniravi", "dotfiles"),
        HubRepository("haniravi", "scripts"),
        HubRepository("haniravi", "sandbox-status-bar"),
        HubRepository("haniravi", "sandbox-text-editor"),
        HubRepository("haniravi", "sandbox-image-viewer"),
        HubRepository("haniravi", "sandbox-terminal-emulator"),
        HubRepository("haniravi", "sandbox"),
        HubRepository("haniravi", "nito")
      )
   }

   "'haniravi/nito'" should "have 'amuradyan' as the only contributor with 46 contributions" in {
      gitHub.listRepositoryContributors("haniravi", "nito") shouldEqual List(
        HubContributor("amuradyan", 46)
      )
   }

   "'dekanat/whiteboard'" should "have 'mushegh-smunch' as the only contributor with 1 contribution" in {
      gitHub.listRepositoryContributors("dekanat", "whiteboard") shouldEqual List(
        HubContributor("mushegh-smunch", 1)
      )
   }

   "'haniravi/sandbox'" should "have no contributors" in {
      gitHub.listRepositoryContributors("haniravi", "sandbox") shouldEqual Nil
   }
