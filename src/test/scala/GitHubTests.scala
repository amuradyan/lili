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

   "'basecamp'" should "have 147 repositories" in {
      gitHub.listOrganizationRepositories("basecamp").size shouldEqual 147
   }

   "'atlassian'" should "have 376 repositories" in {
      gitHub.listOrganizationRepositories("atlassian").size shouldEqual 376
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

   "'microsoft/vscode'" should "have 364 contributors" in {
      gitHub.listRepositoryContributors("microsoft", "vscode").size shouldEqual 364
   }

   "'dekanat'" should "have four public repositories" in {
      gitHub.getOrganizationRepositoryCount("dekanat") shouldEqual 4
   }

   "'haniravi'" should "have nine public repositories" in {
      gitHub.getOrganizationRepositoryCount("haniravi") shouldEqual 9
   }

   "Second page of contributors of 'haniravi/nito'" should "be empty" in {
      val contributorsOnPage2 = gitHub.repositoryContributorListAtPage("haniravi", "nito", 2)

      contributorsOnPage2 shouldEqual Nil
   }

   "Second page of contributors of 'microsoft/typescript'" should " not be empty" in {
      val contributorsOnPage2 = gitHub.repositoryContributorListAtPage("microsoft", "typescript", 2)

      contributorsOnPage2 should not be (Nil)
   }

   "Second page of repos of 'haniravi'" should "be empty" in {
      val reposOnPage2 = gitHub.organizationRepositoryListAtPage("haniravi", 2)

      reposOnPage2 shouldEqual Nil
   }

   "Second page of repos of 'microsoft'" should "not be empty" in {
      val reposOnPage2 = gitHub.organizationRepositoryListAtPage("microsoft", 2)

      reposOnPage2 should not be (Nil)
   }
