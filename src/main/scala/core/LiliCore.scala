package lili.core

import lili.core.hub.VCSHub
import lili.core.models._

object LiliCore:
   def getOrganizationRepositories(organization: String)(implicit hub: VCSHub): Repositories =
      hub.listOrganizationRepositories(organization)

   def getRepositoryContributors(organization: String, repository: String)(implicit hub: VCSHub): Contributors =
      hub.listRepositoryContributors(organization, repository)

   def getOrganizationContributors(organization: String)(implicit hub: VCSHub): Contributors =
      getOrganizationRepositories(organization)
         .flatMap { repository =>
            getRepositoryContributors(organization, repository.name)
         }
         .groupBy(_.login)
         .mapValues {
            _.reduce((l, r) => Contributor(l.id, l.login, l.contributions + r.contributions))
         }
         .values
         .toList

   def getContributorsSortedByContributions(organization: String)(implicit hub: VCSHub): Contributors =
      getOrganizationContributors(organization).sortBy(_.contributions)
