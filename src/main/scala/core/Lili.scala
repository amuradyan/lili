package lili.core

import lili.core.hub.VCSHub
import lili.core.models._

object Lili:
   def getOrganizationRepositories(organization: String)(implicit hub: VCSHub): Repositories =
      hub.listOrganizationRepositories(organization).map(r => Repository(r.owner, r.name))

   def getRepositoryContributors(organization: String, repository: String)(implicit hub: VCSHub): Contributors =
      hub.listRepositoryContributors(organization, repository)
         .map(c => hub.getUser(c.login).map(u => Contributor(c.login, u.name, c.contributions)).get)

   def getOrganizationContributors(organization: String)(implicit hub: VCSHub): Contributors =
      val contributorsGroupedByLoginName =
         getOrganizationRepositories(organization)
            .flatMap { r =>
               getRepositoryContributors(organization, r.name)
            }
            .groupBy(_.login)

      val contributorsSortedByContribution = contributorsGroupedByLoginName
         .mapValues {
            _.reduce((l, r) => Contributor(l.login, l.name, l.contributions + r.contributions))
         }
         .values
         .toList

      contributorsSortedByContribution

   def getContributorsSortedByContributions(organization: String)(implicit hub: VCSHub): Contributors =
      getOrganizationContributors(organization).sortBy(-_.contributions)
