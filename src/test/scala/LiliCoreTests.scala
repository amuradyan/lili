package lili.core.tests

import org.scalatest._
import flatspec._
import matchers._
import org.scalatest.matchers.should.Matchers
import lili.core.Lili
import lili.hubs.VCSHub
import lili.hubs.stubhub.StubHub

class LiliCoreTest extends AnyFlatSpec with Matchers:
   implicit val busyOrgHub: VCSHub = new StubHub

   "Listing contributors of an empty repo" should "result an empty list" in {
      val contributors = Lili.getRepositoryContributors("OrgY", "gamma")

      contributors should be(Nil)
   }

   "Listing contributors of a non-existent repo" should "result in an empty list" in {
      val contributors = Lili.getRepositoryContributors("OrgX", "theta")

      contributors should be(Nil)
   }

   "Listing contributors for a busy repo" should "result in contributors" in {
      val contributors = Lili.getRepositoryContributors("OrgX", "alpha")

      contributors should not be Nil
      contributors.length shouldEqual 3
   }

   "Listing contributors of an empty organization" should "result in an empty list" in {
      val contributors = Lili.getOrganizationContributors("OrgZ")

      contributors should be(Nil)
   }

   "Listing contributors of a busy organization" should "result in contributors, if it contains busy repos" in {
      val contributors = Lili.getOrganizationContributors("OrgX")

      contributors should not be Nil
   }

   "Listing contributors of a busy organization" should "result in nil, if it does not contain busy repos" in {
      val contributors = Lili.getOrganizationContributors("OrgY")

      contributors should be(Nil)
   }

   "Listing organizations contributors" should "not contain duplicates" in {
      val contributors = Lili.getOrganizationContributors("OrgX")

      contributors.size shouldEqual contributors.toSet.size
   }

   "Organization contributor list" should "sum up each contributors contributions" in {
      val markOnAlpha = Lili
         .getRepositoryContributors("OrgX", "alpha")
         .filter(_.login == "mark")
         .head

      val markOnBeta = Lili
         .getRepositoryContributors("OrgX", "beta")
         .filter(_.login == "mark")
         .head

      val markOnBoth = Lili.getOrganizationContributors("OrgX").filter(_.login == "mark").head

      markOnBoth.contributions shouldEqual (markOnAlpha.contributions + markOnBeta.contributions)
   }

   "Fetching the sorted list of contributors" should "result in a descending list by contributions" in {
      val contributors = Lili.getContributorsSortedByContributions("OrgX")

      contributors.sliding(2).foreach {
         case List(a, b) => a.contributions should be >= b.contributions
         case _          => succeed
      }
   }

   "Search in organizations" should "be case insensitive" in {
      val uppercaseRepos = Lili.getOrganizationRepositories("ORGX")
      val lowercaseRepos = Lili.getOrganizationRepositories("orgx")

      uppercaseRepos shouldEqual lowercaseRepos
   }

   "Search in repositories" should "be case insensitive" in {
      val uppercaseContributors = Lili.getRepositoryContributors("ORGX", "ALPHA")
      val lowercaseContributors = Lili.getRepositoryContributors("orgx", "alpha")

      uppercaseContributors shouldEqual lowercaseContributors
   }
