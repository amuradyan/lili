package lili.hubs.github

import lili.hubs._
import lili.core.Lili

import cats.effect._
import cats.effect.unsafe.implicits.global
import org.http4s.client.{Client, JavaNetClientBuilder}
import github4s.Github as GH
import github4s.domain.Repository
import github4s.domain.Pagination

class GitHub extends VCSHub:

   val httpClient: Client[IO] = JavaNetClientBuilder[IO].create

   val accessToken = sys.env.get("GH_TOKEN")

   val gh = GH[IO](httpClient, accessToken)

   def getOrganizationRepositoryCount(organization: String): RepoCount =
      listOrganizationRepositories(organization).length

   def getUserName(login: String): Option[Name] =
      gh.users.get(login).unsafeRunSync().result match
         case Right(u) => u.name
         case Left(_)  => None

   def organizationRepositoryListAtPage(organization: String, page: Int): HubRepositories =
      val paginationOption = Some(Pagination(page, 100))
      gh.repos.listOrgRepos(organization, pagination = paginationOption).unsafeRunSync().result match
         case Right(repos) => repos.map(r => HubRepository(r.owner.login, r.name))
         case Left(_)      => List.empty

   def listOrganizationRepositories(organization: String): HubRepositories =
      gh.repos.listOrgRepos(organization).unsafeRunSync().result match
         case Right(repos) => repos.map(r => HubRepository(r.owner.login, r.name))
         case Left(_)      => List.empty

   def repositoryContributorListAtPage(organization: String, repository: String, page: Int): HubContributors =
      val paginationOption = Some(Pagination(page, 100))
      gh.repos.listContributors(organization, repository, pagination = paginationOption).unsafeRunSync().result match
         case Right(contributors) => contributors.map(c => HubContributor(c.login, c.contributions.getOrElse(0)))
         case Left(_)             => List.empty

   def listRepositoryContributors(organization: String, repository: String): HubContributors =
      gh.repos.listContributors(organization, repository).unsafeRunSync().result match
         case Right(contributors) => contributors.map(c => HubContributor(c.login, c.contributions.getOrElse(0)))
         case Left(_)             => List.empty
