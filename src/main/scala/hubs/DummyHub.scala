package lili.hubs.dummyhub

import lili.hubs._

class DummyHub extends VCSHub:

   private val alpha_contributors = List(
     HubContributor("steve", 1),
     HubContributor("mark", 2),
     HubContributor("phil", 2)
   )
   private val alpha = HubRepository("orgx", "alpha")

   private val beta_contributors = List(
     HubContributor("steve", 2),
     HubContributor("mark", 1),
     HubContributor("phil", 2)
   )
   private val beta = HubRepository("orgx", "beta")

   private val gamma = HubRepository("orgy", "gamma")

   private val organizationsAndRepos = Map(
     "orgx" -> List(alpha, beta),
     "orgy" -> List(gamma),
     "orgz" -> Nil
   )

   private val reposAndContributors = Map(
     alpha -> alpha_contributors,
     beta -> beta_contributors,
     gamma -> Nil
   )

   private val users = List(
     HubUser("steve", "Steve Robinson"),
     HubUser("mark", "Mark Robinson"),
     HubUser("phil", "Phil Robinson")
   )

   def getUser(login: String): Option[HubUser] =
      users.find(_.login.toLowerCase == login.toLowerCase)

   def listOrganizationRepositories(organization: String): HubRepositories =
      organizationsAndRepos.get(organization.toLowerCase) match {
         case Some(repos) => repos
         case None        => Nil
      }

   def listRepositoryContributors(organization: String, repository: String): HubContributors =
      val matchedRepo = listOrganizationRepositories(organization)
         .find(_.fullName.toLowerCase == s"${organization.toLowerCase}/${repository.toLowerCase}")

      matchedRepo match {
         case Some(repo) => reposAndContributors(repo)
         case None       => Nil
      }
