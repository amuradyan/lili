package lili.hubs.github

import lili.hubs._
import lili.core.Lili

import cats.effect._
import cats.effect.unsafe.implicits.global
import org.http4s.client.{Client, JavaNetClientBuilder}
import github4s.Github as GH
import github4s.domain.Repository

class GitHub extends VCSHub:

   val httpClient: Client[IO] = JavaNetClientBuilder[IO].create

   val accessToken = sys.env.get("GITHUB_TOKEN")
   val gh = GH[IO](httpClient, accessToken)

   def getUserName(login: String): Option[Name] =
      gh.users.get(login).unsafeRunSync().result match {
         case Right(u) => u.name
         case Left(_)  => None
      }

   def listOrganizationRepositories(organization: String): HubRepositories = ???

   def listRepositoryContributors(organization: String, repository: String): HubContributors = ???
