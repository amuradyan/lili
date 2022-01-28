package lili.core.hub.dummyhub

import lili.core.hub._

class DummyHub extends VCSHub:

   private val alpha_contributors = List(
     HubContributor("steve", 1),
     HubContributor("mark", 2),
     HubContributor("phil", 2)
   )
   private val alpha = HubRepository("OrgX", "alpha")

   private val beta_contributors = List(
     HubContributor("steve", 2),
     HubContributor("mark", 1),
     HubContributor("phil", 2)
   )
   private val beta = HubRepository("OrgX", "beta")

   private val gamma = HubRepository("OrgY", "gamma")

   private val organizationsAndRepos = Map(
     "OrgX" -> List(alpha, beta),
     "OrgY" -> List(gamma),
     "OrgZ" -> Nil
   )

   private val reposAndContributors = Map(
     alpha -> alpha_contributors,
     beta -> beta_contributors,
     gamma -> Nil
   )

   private val users = List(
     HubUser("steve", "Steve"),
     HubUser("mark", "Mark"),
     HubUser("phil", "Phil")
   )

   def getUser(login: String): Option[HubUser] = users.find(_.login == login)

   def listOrganizationRepositories(organization: String): HubRepositories =
      organizationsAndRepos.get(organization) match {
         case Some(repos) => repos
         case None        => Nil
      }

   def listRepositoryContributors(organization: String, repository: String): HubContributors = {
      val matchedRepo = listOrganizationRepositories(organization)
         .find(_.fullName == s"$organization/$repository")

      matchedRepo match {
         case Some(repo) => reposAndContributors(repo)
         case None       => Nil
      }
   }
