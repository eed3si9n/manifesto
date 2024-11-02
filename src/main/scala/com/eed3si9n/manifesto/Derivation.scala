package com.eed3si9n.manifesto

import scala.reflect.TypeTest
import scala.quoted.*

// https://github.com/scala/scala3/blob/3.5.1/compiler/src/scala/quoted/runtime/impl/QuotesImpl.scala#L1774

object Derivation:
  inline def derived[A1]: Manifesto[A1] = ${ derivedMacro[A1] }
  def derivedMacro[A1: Type](using ctx: Quotes): Expr[Manifesto[A1]] =
    import ctx.reflect.*
    val tpe = Type.of[A1]
    def maybeApplied(repr: TypeRepr)(using TypeTest[TypeRepr, AppliedType]) = repr match
      case a @ AppliedType(con, args) => Some(a)
      case _                          => None
    def mkApplied[A2: Type](
        typeCon0: String,
        args0: List[Expr[Manifesto[?]]],
    ): Expr[Manifesto[A2]] =
      '{
        Manifesto.Applied[A2](
          ${ Expr(typeCon0) },
          List(${ Varargs(args0) }*)
        )
      }
    def mkSingleton[A2: Type](typeName: String): Expr[Manifesto[A2]] =
      '{ Manifesto.Singleton[A2](${ Expr(typeName) }) }
    def mkFallback[A2: Type](typeName: String): Expr[Manifesto[A2]] =
      '{ Manifesto.Fallback[A2](${ Expr(typeName) }) }
    def fromTypeRepr[A2: Type](repr: TypeRepr): Expr[Manifesto[A2]] =
      val show0 = repr.show(using Printer.TypeReprCode)
      val show1 =
        if repr.isSingleton then s"$show0.type"
        else if show0 == "scala.Predef.String" then "java.lang.String"
        else show0
      if repr.isSingleton then mkSingleton[A2](show1)
      else mkFallback[A2](show1)
    def fromTypeRepr2[A2: Type](
        repr: TypeRepr,
        con: TypeRepr,
        args: List[TypeRepr],
    ): Expr[Manifesto[A2]] =
      val con0 = con.show(using Printer.TypeReprCode)
      val args0 = args.map: (arg) =>
        arg.asType match
          case '[a] =>
            maybeApplied(arg) match
              case Some(AppliedType(con, args)) => fromTypeRepr2[a](arg, con, args)
              case _                            => fromTypeRepr[a](arg)
      mkApplied[A2](con0, args0)
    val repr = TypeRepr.of[A1]
    maybeApplied(repr) match
      case Some(AppliedType(con, args)) => fromTypeRepr2[A1](repr, con, args)
      case _                            => fromTypeRepr[A1](repr)
end Derivation
