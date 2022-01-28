package lili.core.hub

import lili.core.models._

trait VCSHub:
   def listOrganizationRepositories(organization: String): Repositories
   def listRepositoryContributors(organization: String, repository: String): Contributors
