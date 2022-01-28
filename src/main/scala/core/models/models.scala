package lili.core.models

case class Contributor(id: Long, login: String, contributions: Int)

type Contributors = List[Contributor]

case class Repository(id: Long, owner: String, name: String):
   def fullName: String = owner + "/" + name

type Repositories = List[Repository]
