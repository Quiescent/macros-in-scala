package macros

import scala.reflect.macros.blackbox.Context

object IntroduceSymbols {
  class Impl(val c: Context) {
    def introduceTestOneArg(
        f: c.Expr[String => String => String]
    ): c.Expr[String => String] = {
      import c.universe._
      val positions = reify{Seq(1)}
      c.Expr(
        q"""((b: String) => {
              val splitted = b.split("/")
              val values = $positions map (position => splitted(position))
              $f(values(0))(b)
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
  def introduceTestOneArg(f: String => String => String): String => String =
    macro Impl.introduceTestOneArg

  @SuppressWarnings(Array("org.wartremover.warts.Null"))
  def introduceTestTwoArgs(
      f: String => String => String => String
  ): String => String = macro Impl.introduceTestTwoArgs

  @SuppressWarnings(Array("org.wartremover.warts.Null"))
  def introduceTestThreeArgs(
      f: String => String => String => String => String
  ): String => String = macro Impl.introduceTestThreeArgs
}
