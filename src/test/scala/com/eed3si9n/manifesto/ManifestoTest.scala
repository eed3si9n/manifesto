package com.eed3si9n.manifesto

object ManifestoTest extends verify.BasicTestSuite:
  test("Int") {
    val m = Manifesto[Int]
    assert(m == Manifesto[Int])
    assert(m.show == "scala.Int")
  }

  test("Unit") {
    val m = Manifesto[Unit]
    assert(m != Manifesto[Int])
    assert(m.show == "scala.Unit")
  }

  test("Symbol") {
    val m = Manifesto[Symbol]
    assert(m != Manifesto[Int])
    assert(m.show == "scala.Symbol")
  }

  test("List[Int]") {
    val m = Manifesto[List[Int]]
    assert(m != null)
    assert(m.show == "scala.collection.immutable.List[scala.Int]")
    assert(m.typeCon == "scala.collection.immutable.List")
    val arg0 = m.typeArguments(0)
    assert(arg0.show == "scala.Int")
  }

  test("(Int, Int, String)") {
    val m = Manifesto[(Int, Int, String)]
    assert(m != null)
    assert(m.show == "scala.Tuple3[scala.Int,scala.Int,java.lang.String]")
    assert(m.typeCon == "scala.Tuple3")
    assert(m.typeArguments(0) == m.typeArguments(1))
  }

  test("Option[List[Int]]") {
    val m = Manifesto[Option[List[Int]]]
    assert(m != null)
    assert(m.show == "scala.Option[scala.collection.immutable.List[scala.Int]]")
    assert(m.typeCon == "scala.Option")
    assert(m.typeArguments(0).typeCon == "scala.collection.immutable.List")
  }

  test("Seq[(String, URI)]") {
    val m = Manifesto[Seq[(String, java.net.URI)]]
    assert(m != null)
    assert(
      m.show == "scala.collection.immutable.Seq[scala.Tuple2[java.lang.String,java.net.URI]]"
    )
    assert(m.typeCon == "scala.collection.immutable.Seq")
    assert(m.typeArguments(0).typeCon == "scala.Tuple2")
  }

  test("1") {
    val m = Manifesto[1]
    assert(m.show == "1.type")
  }

  test("Foo.type") {
    val m = Manifesto[Foo.type]
    assert(m.show == "com.eed3si9n.manifesto.ManifestoTest.Foo.type")
  }

  test("Int | String") {
    val m = Manifesto[Int | String]
    assert(m.show == "scala.Int | java.lang.String")
  }

  object Foo
end ManifestoTest
