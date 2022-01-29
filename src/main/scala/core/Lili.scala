package lili.core

import lili.hubs.VCSHub
import lili.core.models._

object Lili:
   def getOrganizationRepositories(organization: String)(implicit hub: VCSHub): Repositories =
      hub.listOrganizationRepositories(organization).map(r => Repository(r.owner, r.name))

   def getRepositoryContributors(organization: String, repository: String)(implicit hub: VCSHub): Contributors =
      hub
         .listRepositoryContributors(organization, repository)
         .map { c =>
            hub.getUserName(c.login) match
               case Some(name) => Contributor(c.login, name, c.contributions)
               case None       => Contributor(c.login, "", c.contributions)
         }

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
