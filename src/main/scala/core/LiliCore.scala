package lili.core

import lili.core.hub.VCSHub
import lili.core.models._

object LiliCore:
   def getOrganizationRepositories(organization: String)(implicit hub: VCSHub): Repositories =
      hub.listOrganizationRepositories(organization).map(r => Repository(r.name, r.owner))

   def getRepositoryContributors(organization: String, repository: String)(implicit hub: VCSHub): Contributors = ???

   def getOrganizationContributors(organization: String)(implicit hub: VCSHub): Contributors =
      val contributorsGroupedByLoginName = getOrganizationRepositories(organization)
         .flatMap { repository =>
            getRepositoryContributors(organization, repository.name)
         }
         .groupBy(_.login)

      contributorsGroupedByLoginName
         .mapValues {
            _.reduce((l, r) => Contributor(l.login, l.name, l.contributions + r.contributions))
         }
         .values
         .toList

   def getContributorsSortedByContributions(organization: String)(implicit hub: VCSHub): Contributors =
      getOrganizationContributors(organization).sortBy(-_.contributions)
