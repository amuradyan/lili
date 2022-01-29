# Lili 📼

_Lili_ is the [scalac](https://scalac.io/) technical interview task. It fetches the list of contributors of a given GitHub organization, sorted by number of contributions in a descending order. 

## Stack

To achieve this, it's frontend will be an [http4s](https://http4s.org/) server with a single endpoint. It's backend will rely on [Github4s](https://47degrees.github.io/github4s/), which is a library /built on [GitHub REST API V3](https://developer.github.com/v3/)/ to communicate with GitHub.

## Usage

_Lili_ serves the information over HTTP at port `8080` at endpoint `/org/<organization name>/contributors`.

If you host it locally, it should be to reach it like so:

```bash
curl http://localhost:8080/org/<organization name>/contributors
```

## Build, test, run

[sbt](https://www.scala-sbt.org/) is used to build the project.

To clean, compile and test the project, run the following command:

```bash
sbt clean test
```

To run the project, do the following:

```bash
sbt run
```

Assuming all went well, _Lili_ is now listening at `localhost:8080`.

## uber-jar

To create a deployable jar, you should run the following command:

```bash
sbt assembly
```

After that, you'll find the jar at `./target/scala-3.1.0/lili-assembly-x.y.z.jar` from the project root.

To run it, do the following:

```bash
java -jar lili-assembly-x.y.z.jar
```

This will start _Lili_ on `localhost:8080`.

Know, however, that you are restricted to 60 requests to GitHub an hour in this case /note: one _Lili_ request uses several GitHub requests/. If you need more requests, you will have to use GitHub [tokens](https://github.com/settings/tokens/new). 

You can pass your token to _Lili_ via an environment variable `GH_TOKEN` either explicitly, or through a/n/ `.env` file. The command to run would now look like this:


```bash
export GH_TOKEN=<your token>; java -jar lili-assembly-x.y.z.jar
```

**NOTE:** You can find out more about tokens and rates at [GitHub Rate Limiting](https://docs.github.com/en/rest/overview/resources-in-the-rest-api#rate-limiting).


## Layout

_Lili_ is a [Cats Effect](https://typelevel.org/cats-effect/docs/getting-started) IO app, consisting of two main pieces: the core and the hubs. 

The core is the business logic /a small scala object/ and a the types: `Contributor`, `Repository`, and their lists.

The hubs consist of an abstraction of a version control system hub /`VCSHub`/, and two implementations: `GitHub` and `StubHub`.


## Thoughts

Overall, this was very interesting for me. Here are some thoughts.

Test of all sorts are all piled together. It would be nice to separate them by types and use appropriate tools and techniques. This process will also reveal holes. Nevertheless, the current tests allow for confident experimenting.

_Lili_ is requesting the data sequentially, so things are slow. There are a few fairly easy ways to fix this, though.
