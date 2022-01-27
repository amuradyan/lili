package lili_core.tests

import lili_core.hub.VCSHub
import lili_core.models._

class DummyHub extends VCSHub:

   val alpha_contributors = List(
     Contributor(1, "steve", 1),
     Contributor(2, "mark", 2),
     Contributor(3, "phil", 2)
   )
   val alpha = Repository(1, "OrgX", "alpha")

   val beta_contributors = List(
     Contributor(1, "steve", 2),
     Contributor(2, "mark", 1),
     Contributor(3, "phil", 2)
   )
   val beta = Repository(2, "OrgX", "beta")

   val gamma = Repository(3, "OrgY", "gamma")

   val organizationsAndRepos = Map(
     "OrgX" -> List(alpha, beta),
     "OrgY" -> List(gamma),
     "OrgZ" -> Nil
   )

   val reposAndContributors = Map(
     alpha -> alpha_contributors,
     beta -> beta_contributors,
     gamma -> Nil
   )

   def listOrganizationRepositories(organization: String): Repositories =
      organizationsAndRepos.get(organization) match {
         case Some(repos) => repos
         case None        => Nil
      }

   def listRepositoryContributors(organization: String, repository: String): Contributors = {
      val matchedRepo = listOrganizationRepositories(organization)
         .find(_.fullName == s"$organization/$repository")

      matchedRepo match {
         case Some(repo) => reposAndContributors(repo)
         case None       => Nil
      }
   }
