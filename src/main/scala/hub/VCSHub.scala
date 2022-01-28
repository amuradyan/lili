package lili_core.hub

import lili_core.models._

trait VCSHub:
   type Contributors = List[Contributor]
   type Repositories = List[Repository]

   def listOrganizationRepositories(organization: String): Repositories
   def listRepositoryContributors(organization: String, repository: String): Contributors
   def getOrganizationRepositoryCount(organization: String): Int
