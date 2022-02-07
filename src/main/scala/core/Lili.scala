package lili.core

import lili.hubs.VCSHub
import lili.core.models._

class Lili(hub: VCSHub):
   def getOrganizationRepositories(organization: String): Repositories =
      hub.listOrganizationRepositories(organization).map(r => Repository(r.owner, r.name))

   def getRepositoryContributors(organization: String, repository: String): Contributors =
      hub
         .listRepositoryContributors(organization, repository)
         .map { c => Contributor(c.login, c.contributions) }

   def getOrganizationContributors(organization: String): Contributors =
      val contributorsGroupedByLoginName =
         getOrganizationRepositories(organization)
            .flatMap { r =>
               getRepositoryContributors(organization, r.name)
            }
            .groupBy(_.name)

      val contributorsSortedByContribution = contributorsGroupedByLoginName
         .mapValues {
            _.reduce((l, r) => Contributor(l.name, l.contributions + r.contributions))
         }
         .values
         .toList

      contributorsSortedByContribution

   def getContributorsSortedByContributions(organization: String): Contributors =
      getOrganizationContributors(organization).sortBy(-_.contributions)
