package lili_core

case class Contributor(id: Long, login: String, contributions: Int)
type Contributors = List[Contributor]

case class Repository(id: Long, name: String, contributors: Contributors)
type Repositories = List[Repository]

case class Organization(id: Long, name: String, repositories: Repositories)
type Organizations = List[Organization]

trait VCSHub:
  def listOrganizationRepositories(organization: String): Repositories
  def listRepositoryContributors(organization: String, repository: String): Contributors

object LiliCore:
  type Contributors = List[Contributor]
  type Repositories = List[String]

  def getOrganizationRepositories(organization: String)(implicit hub: VCSHub): Unit = ???

  def getRepositoryContributors(repository: String)(implicit hub: VCSHub): Contributors = ???

  def getOrganizationContributors(organization: String)(implicit hub: VCSHub): Contributors = ???
