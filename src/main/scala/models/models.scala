package lili_core.models

class Contributor(val id: Long, val login: String, val contributions: Int):
   override def equals(other: Any): Boolean = other match {
      case that: Contributor => id == that.id && login == that.login
      case _                 => false
   }

type Contributors = List[Contributor]

case class Repository(id: Long, owner: String, name: String):
   def fullName: String = owner + "/" + name

type Repositories = List[Repository]
