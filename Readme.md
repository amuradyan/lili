# Lili 📼

_Lili_ is the [scalac](https://scalac.io/) interview task. It is supposed to be something, that fetches the list of contributors of a given GitHub organization, sorted by number of contributions in a descending order. 

## Stack

To achieve this, it's frontend will be an [http4s](https://http4s.org/) server with a single endpoint. It's backend will rely on [Github4s](https://47degrees.github.io/github4s/), which is a library /built on [GitHub REST API V3](https://developer.github.com/v3/)/ to communicate with GitHub.

## Usage

_Lili_ serves the information over HTTP at port `8080` at endpoint `/org/_name_/contributors`.

If you host it locally, it should be to reach it like so.:
> curl http://localhost:8080/org/<_organization name_>/contributors

## Build, test, run

[sbt](https://www.scala-sbt.org/) is used to build the project.

To clean, compile and test the project, run the following command:

> sbt clean test

To run the project, do the following:

> sbt run

Assuming all went well, _Lili_ is now listening at `localhost:8080`.

## uber-jar

To create a deployable jar, you should run the following command:
> sbt assembly

After that, you'll find the jar at `./target/scala-3.1.0/Lili-assembly-0.1.0-SNAPSHOT.jar` from the project root.

To run it, do the following:

> java -jar Lili-assembly-0.1.0-SNAPSHOT.jar

This will start _Lili_ on `localhost:8080`.

Know, however, that you are restricted to 60 requests to GitHub an hour in this case /note: one _Lili_ request uses several GitHub requests/. If you need more requests, you will have to use GitHub tokens. You can find out more at the [GitHub Rate Limiting](https://docs.github.com/en/rest/overview/resources-in-the-rest-api#rate-limiting).

You can pass your token to _Lili_ via an environment variable `GH_TOKEN` either explicitly, or through a `.env` file. The command to run would now look like this:

> export GH_TOKEN=<_your token_>; java -jar Lili-assembly-0.1.0-SNAPSHOT.jar

## Layout

_Lili_ consists of two main pieces: the core and the hubs. 

The core is the business logic /a small scala object/ and a the types: `Contributor`, `Repository`, and their lists.

The hubs consist of an abstraction of a version control system hub, and two implementations: `GitHub` and `DummyHub`.

## Thoughts

This was interesting for me.

