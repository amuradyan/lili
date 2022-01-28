package lili.core.hub.test

import org.scalatest._
import flatspec._
import matchers._
import org.scalatest.matchers.should.Matchers
import lili.core.hub.dummyhub.DummyHub

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
