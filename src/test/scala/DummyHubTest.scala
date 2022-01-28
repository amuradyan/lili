package lili.core.hub.test

import org.scalatest._
import flatspec._
import matchers._
import org.scalatest.matchers.should.Matchers
import lili.hubs.dummyhub.DummyHub

class DummyHubTest extends AnyFlatSpec with Matchers:
   val dummyHub = new DummyHub()

   "Listing repos of an empty organization" should "result in an empty list" in {
      val repos = dummyHub.listOrganizationRepositories("OrgZ")

      repos should be(Nil)
   }

   "Listing repos of a non-existent organization" should "result in an empty list" in {
      val repos = dummyHub.listOrganizationRepositories("OrgNE")

      repos should be(Nil)
   }

   "Listing repos for a busy organization" should "result in a list of repos" in {
      val repos = dummyHub.listOrganizationRepositories("OrgX")

      repos should not be Nil
      repos.length shouldEqual 2
   }

   "Listing contributors of an empty repo" should "result an empty list" in {
      val contributors = dummyHub.listRepositoryContributors("OrgY", "gamma")

      contributors should be(Nil)
   }

   "Listing contributors of a non-existent repo" should "result in an empty list" in {
      val contributors = dummyHub.listRepositoryContributors("OrgX", "theta")

      contributors should be(Nil)
   }

   "Listing contributors for a busy repo" should "result in contributors" in {
      val contributors = dummyHub.listRepositoryContributors("OrgX", "alpha")

      contributors should not be Nil
      contributors.length shouldEqual 3
   }

   "Searching for an empty login" should "not yield a result" in {
      val contributors = dummyHub.getUser("")

      contributors shouldEqual None
   }

   "Searching for a non-existent login" should "not yield a result" in {
      val contributors = dummyHub.getUser("---")

      contributors shouldEqual None
   }

   "Searching for an existing login" should "yield the corresponding user" in {
      val contributors = dummyHub.getUser("steve")

      contributors.isEmpty shouldEqual false
      contributors.get.login shouldEqual "steve"
   }

   "Search in organizations" should "be case insensitive" in {
      val uppercaseRepos = dummyHub.listOrganizationRepositories("ORGX")
      val lowercaseRepos = dummyHub.listOrganizationRepositories("orgx")

      uppercaseRepos shouldEqual lowercaseRepos
   }

   "Search in repositories" should "be case insensitive" in {
      val uppercaseContributors = dummyHub.listRepositoryContributors("ORGX", "ALPHA")
      val lowercaseContributors = dummyHub.listRepositoryContributors("orgx", "alpha")

      uppercaseContributors shouldEqual lowercaseContributors
   }

   "Search for users" should "be case insensitive" in {
      val uppercaseUser = dummyHub.getUser("STEVE")
      val lowercaseUser = dummyHub.getUser("steve")

      uppercaseUser.isEmpty shouldEqual false
      lowercaseUser.isEmpty shouldEqual false
      uppercaseUser shouldEqual lowercaseUser
   }
