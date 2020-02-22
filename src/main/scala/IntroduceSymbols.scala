package macros

import scala.reflect.macros.blackbox.Context

object IntroduceSymbols {
  trait Parser[T] {
    def parse(x: String): T
  }

  class Impl(val c: Context) {
    @SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
    def introduceTestOneArg[T: c.WeakTypeTag](
        f: c.Expr[T => String => String]
    ): c.Expr[String => String] = {
      import c.universe._
      val positions = reify { Seq(1) }

      val T = c.weakTypeOf[T]

      c.Expr[String => String](q"""((b: String) => {
              val splitted = b.split("/")
              val values = $positions map (position => splitted(position))
              val arg1 = implicitly[macros.IntroduceSymbols.Parser[$T]].parse(values(0))
              $f(arg1)(b)
            })""")
    }

    def introduceTestTwoArgs(
        f: c.Expr[String => String => String => String]
    ): c.Expr[String => String] = {
      import c.universe._
      c.Expr(q"""$f("test")""")
    }

    def introduceTestThreeArgs(
        f: c.Expr[String => String => String => String => String]
    ): c.Expr[String => String] = {
      import c.universe._
      c.Expr(q"""$f("test")""")
    }
  }

  @SuppressWarnings(Array("org.wartremover.warts.Null"))
  def introduceTestOneArg[T](f: T => String => String): String => String =
    macro Impl.introduceTestOneArg[T]

  @SuppressWarnings(Array("org.wartremover.warts.Null"))
  def introduceTestTwoArgs(
      f: String => String => String => String
  ): String => String = macro Impl.introduceTestTwoArgs

  @SuppressWarnings(Array("org.wartremover.warts.Null"))
  def introduceTestThreeArgs(
      f: String => String => String => String => String
  ): String => String = macro Impl.introduceTestThreeArgs
}
