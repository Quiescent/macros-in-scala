package macros

import scala.reflect.macros.blackbox.Context

object IntroduceSymbols {
  class Impl(val c: Context) {
    def introduceTest(tree: c.Tree): c.Tree = {
      import c.universe._
      q"""// def blah(test: String) {
          //   $$tree
          // }
          // blah("hehe")
          $tree
       """
    }
  }

  @SuppressWarnings(Array("org.wartremover.warts.Null"))
  def introduceTest(tree: String => String): String => String = macro Impl.introduceTest
}
