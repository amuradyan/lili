package lili.core.models

case class Contributor(login: String, name: String, contributions: Int)

type Contributors = List[Contributor]

case class Repository(owner: String, name: String):
   def fullName: String = owner + "/" + name

type Repositories = List[Repository]
