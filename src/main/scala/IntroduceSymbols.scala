package macros

import scala.reflect.macros.blackbox.Context

object IntroduceSymbols {
  class Impl(val c: Context) {
    def introduceTestOneArg(
        f: c.Expr[String => String => String]
    ): c.Expr[String => String] = {
      import c.universe._
      c.Expr(q"""$f("test")""")
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
