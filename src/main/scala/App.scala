package lili

import cats._
import cats.effect._
import cats.implicits._

import io.circe._
import io.circe.syntax._
import io.circe.generic.auto._

import org.http4s._
import org.http4s.circe._
import org.http4s.server._
import org.http4s.dsl._
import org.http4s.dsl.impl._
import org.http4s.implicits._

import lili.core.Lili
import lili.core.hub.dummyhub.DummyHub

object LiliApp extends IOApp:
   def contributorRoutes[F[_]: Monad]: HttpRoutes[F] = {
      val dsl = Http4sDsl[F]
      import dsl._

      HttpRoutes.of[F] {
         case GET -> Root / "org" / organizationName / "contributors" => {
            val contributors =
               Lili.getContributorsSortedByContributions(organizationName)(DummyHub())

            Ok(contributors.asJson)
         }
      }
   }

   def lili[F[_]: Monad]: HttpApp[F] =
      contributorRoutes[F].orNotFound

   override def run(args: List[String]): IO[ExitCode] =
      import org.http4s.server.blaze._

      BlazeServerBuilder[IO](runtime.compute)
         .bindHttp(8080, "localhost")
         .withHttpApp(lili)
         .resource
         .use(_ => IO.never)
         .as(ExitCode.Success)
