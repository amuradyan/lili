package lili

import cats._
import cats.effect._
import cats.implicits._

import org.http4s._
import org.http4s.server._
import org.http4s.dsl._
import org.http4s.dsl.impl._
import org.http4s.implicits._

object Lili extends IOApp:
   def contributorRoutes[F[_]: Monad]: HttpRoutes[F] = {
      val dsl = Http4sDsl[F]
      import dsl._

      HttpRoutes.of[F] { case GET -> Root / "org" / organizationName / "contributors" =>
         Ok(organizationName)
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
