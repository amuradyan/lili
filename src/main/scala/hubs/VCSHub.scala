package lili.hubs

case class HubUser(login: String, name: String)

case class HubContributor(login: String, contributions: Int)

type HubContributors = List[HubContributor]

case class HubRepository(owner: String, name: String):
   def fullName: String = owner + "/" + name

type HubRepositories = List[HubRepository]

type Name = String
type RepoCount = Int

trait VCSHub:

   def getUserName(login: String): Option[Name]
   def listOrganizationRepositories(organization: String): HubRepositories
   def listRepositoryContributors(organization: String, repository: String): HubContributors
