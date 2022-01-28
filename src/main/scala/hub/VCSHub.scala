package lili_core.hub

import lili_core.models._

trait VCSHub:
   def listOrganizationRepositories(organization: String): Repositories
   def listRepositoryContributors(organization: String, repository: String): Contributors
