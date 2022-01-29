package lili.hubs.tests

import org.scalatest._
import flatspec._
import matchers._
import org.scalatest.matchers.should.Matchers
import lili.hubs.stubhub.StubHub

class StubHubTest extends AnyFlatSpec with Matchers:
   val stubHub = new StubHub()

   "Listing repos of an empty organization" should "result in an empty list" in {
      val repos = stubHub.listOrganizationRepositories("OrgZ")

      repos should be(Nil)
   }

   "Listing repos of a non-existent organization" should "result in an empty list" in {
      val repos = stubHub.listOrganizationRepositories("OrgNE")

      repos should be(Nil)
   }

   "Listing repos for a busy organization" should "result in a list of repos" in {
      val repos = stubHub.listOrganizationRepositories("OrgX")

      repos should not be Nil
      repos.length shouldEqual 2
   }

   "Listing contributors of an empty repo" should "result an empty list" in {
      val contributors = stubHub.listRepositoryContributors("OrgY", "gamma")

      contributors should be(Nil)
   }

   "Listing contributors of a non-existent repo" should "result in an empty list" in {
      val contributors = stubHub.listRepositoryContributors("OrgX", "theta")

      contributors should be(Nil)
   }

   "Listing contributors for a busy repo" should "result in contributors" in {
      val contributors = stubHub.listRepositoryContributors("OrgX", "alpha")

      contributors should not be Nil
      contributors.length shouldEqual 3
   }

   "Searching for an empty login" should "not yield a result" in {
      val contributors = stubHub.getUserName("")

      contributors shouldEqual None
   }

   "Searching for a non-existent login" should "not yield a result" in {
      val contributors = stubHub.getUserName("---")

      contributors shouldEqual None
   }

   "Searching for an existing login" should "yield the corresponding user" in {
      stubHub.getUserName("steve").map { name =>
         name shouldEqual "Steve Robinson"
      }
   }

   "Search in organizations" should "be case insensitive" in {
      val uppercaseRepos = stubHub.listOrganizationRepositories("ORGX")
      val lowercaseRepos = stubHub.listOrganizationRepositories("orgx")

      uppercaseRepos shouldEqual lowercaseRepos
   }

   "Search in repositories" should "be case insensitive" in {
      val uppercaseContributors = stubHub.listRepositoryContributors("ORGX", "ALPHA")
      val lowercaseContributors = stubHub.listRepositoryContributors("orgx", "alpha")

      uppercaseContributors shouldEqual lowercaseContributors
   }

   "Search for users" should "be case insensitive" in {
      val uppercaseUser = stubHub.getUserName("STEVE")
      val lowercaseUser = stubHub.getUserName("steve")

      uppercaseUser.isEmpty shouldEqual false
      lowercaseUser.isEmpty shouldEqual false
      uppercaseUser shouldEqual lowercaseUser
   }

   "OrgX" should "have 2 repos" in {
      val repos = stubHub.listOrganizationRepositories("OrgX")

      repos.length shouldEqual 2
   }

   "OrgY" should "have 1 repo" in {
      val repos = stubHub.listOrganizationRepositories("OrgY")

      repos.length shouldEqual 1
   }

   "OrgZ" should "have 0 repos" in {
      val repos = stubHub.listOrganizationRepositories("OrgZ")

      repos.length shouldEqual 0
   }
