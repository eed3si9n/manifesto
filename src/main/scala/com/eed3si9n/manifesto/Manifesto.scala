package com.eed3si9n.manifesto

sealed trait Manifesto[A1]:
  def typeCon: String
  def typeArguments: List[Manifesto[?]]
  def isSingleton: Boolean
  override def toString(): String = show
  def show: String =
    if typeArguments.isEmpty then typeCon
    else s"""$typeCon[${typeArguments.mkString(",")}]"""

  override def hashCode(): Int = (show, typeCon, typeArguments).##
  override def equals(that: Any): Boolean =
    that match
      case o: Manifesto[?] =>
        this.show == o.show
        && this.typeCon == o.typeCon
        && this.typeArguments == o.typeArguments
      case _ => false
end Manifesto

object Manifesto:
  def apply[A1: Manifesto]: Manifesto[A1] = summon[Manifesto[A1]]
  inline given [A1]: Manifesto[A1] = Derivation.derived[A1]

  class Applied[A1](
      typeCon0: String,
      args0: List[Manifesto[?]]) extends Manifesto[A1]:
    override val typeCon = typeCon0
    override val typeArguments = args0
    override val isSingleton = false

  class Singleton[A1](typeName: String) extends Manifesto[A1]:
    override val typeCon = typeName
    override val typeArguments = Nil
    override val isSingleton = true

  class Fallback[A1](typeName: String) extends Manifesto[A1]:
    override val typeCon = typeName
    override val typeArguments = Nil
    override val isSingleton = false
end Manifesto
