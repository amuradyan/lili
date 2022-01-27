package lili_core

class Contributor(val id: Long, val login: String, val contributions: Int):
   override def equals(other: Any): Boolean = other match {
      case that: Contributor => id == that.id && login == that.login
      case _                 => false
   }

type Contributors = List[Contributor]

case class Repository(id: Long, owner: String, name: String):
   def fullName: String = owner + "/" + name

type Repositories = List[Repository]

trait VCSHub:
   type Contributors = List[Contributor]
   type Repositories = List[Repository]

   def listOrganizationRepositories(organization: String): Repositories
   def listRepositoryContributors(organization: String, repository: String): Contributors

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
