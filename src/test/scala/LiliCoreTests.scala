package lili_core.tests

import org.scalatest._
import flatspec._
import matchers._
import org.scalatest.matchers.should.Matchers
import lili_core.LiliCore
import lili_core.hub.VCSHub

class LiliCoreTest extends AnyFlatSpec with Matchers:
   implicit val busyOrgHub: VCSHub = new DummyHub

   "Listing repos of an empty organization" should "result in an empty list" in {
      val repos = LiliCore.getOrganizationRepositories("OrgZ")

      repos should be(Nil)
   }

   "Listing repos of a non-existent organization" should "result in an empty list" in {
      val repos = LiliCore.getOrganizationRepositories("OrgNE")

      repos should be(Nil)
   }

   "Listing repos for a busy organization" should "result in a list of repos" in {
      val repos = LiliCore.getOrganizationRepositories("OrgX")

      repos should not be Nil
      repos.length shouldEqual 2
   }

   "Listing contributors of an empty repo" should "result an empty list" in {
      val contributors = LiliCore.getRepositoryContributors("OrgY", "gamma")

      contributors should be(Nil)
   }

   "Listing contributors of a non-existent repo" should "result in an empty list" in {
      val contributors = LiliCore.getRepositoryContributors("OrgX", "theta")

      contributors should be(Nil)
   }

   "Listing contributors for a busy repo" should "result in contributors" in {
      val contributors = LiliCore.getRepositoryContributors("OrgX", "alpha")

      contributors should not be Nil
      contributors.length shouldEqual 3
   }

   "Listing contributors of an empty organization" should "result in an empty list" in {
      val contributors = LiliCore.getOrganizationContributors("OrgZ")

      contributors should be(Nil)
   }

   "Listing contributors of a busy organization" should "result in contributors, if it contains busy repos" in {
      val contributors = LiliCore.getOrganizationContributors("OrgX")

      contributors should not be Nil
   }

   "Listing contributors of a busy organization" should "result in nil, if it does not contain busy repos" in {
      val contributors = LiliCore.getOrganizationContributors("OrgY")

      contributors should be(Nil)
   }

   "Listing organizations contributors" should "not contain duplicates" in {
      val contributors = LiliCore.getOrganizationContributors("OrgX")

      contributors.size shouldEqual contributors.toSet.size
   }

   "Organization contributor list" should "sum up each contributors contributions" in {
      val markOnAlpha = LiliCore
         .getRepositoryContributors("OrgX", "alpha")
         .filter(_.login == "mark")
         .head

      val markOnBeta = LiliCore
         .getRepositoryContributors("OrgX", "beta")
         .filter(_.login == "mark")
         .head

      val markOnBoth = LiliCore.getOrganizationContributors("OrgX").filter(_.login == "mark").head

      markOnBoth.contributions shouldEqual (markOnAlpha.contributions + markOnBeta.contributions)
   }

   "Fetching the sorted list of contributors" should "result in a descending list by contributions" in {
      val contributors = LiliCore.getContributorsSortedByContributions("OrgX")

      contributors.sliding(2).foreach {
         case List(a, b) => a.contributions should be <= b.contributions
         case _          => succeed
      }
   }

   "For an empty organization, the count of repos" should "be zero" in {
      val repositoryCount = LiliCore.getOrganizationRepositoryCount("OrgZ")

      repositoryCount shouldEqual 0
   }

   "For a busy organization, the count of repos" should "be greater than zero" in {
      val xRepositoryCount = LiliCore.getOrganizationRepositoryCount("OrgX")
      val yRepositoryCount = LiliCore.getOrganizationRepositoryCount("OrgY")

      xRepositoryCount shouldEqual 2
      yRepositoryCount shouldEqual 1
   }
