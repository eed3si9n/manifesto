Scala 3 Manifesto
=================

Manifesto is a small library to re-implement subset of `scala.reflect.Manifest` in Scala 3.
For details, see <https://eed3si9n.com/manifesto/>.

### equals

```scala
package example

import com.eed3si9n.manifesto.Manifesto

@main def hello(): Unit =
  println(foo(List("hi"))) // xs is List[String]

def foo[A1: Manifesto](xs: A1): String =
  val m = Manifesto[A1]
  val mli = Manifesto[List[Int]]
  val mls = Manifesto[List[String]]
  xs match
    case xs: List[Int]    if m == mli => "xs is List[Int]"
    case xs: List[String] if m == mls => "xs is List[String]"
```

### typeArguments

```scala
package example

import com.eed3si9n.manifesto.Manifesto

@main def hello(): Unit =
  println(bar(List("hi"))) // java.lang.String

def bar[A1: Manifesto](xs: A1): String =
  val m = Manifesto[A1]
  m.typeArguments(0).show
```
