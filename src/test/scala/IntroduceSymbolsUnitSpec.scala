package macros

import org.specs2.mutable.Specification

class IntroduceSymbolsUnitSpec extends Specification {
  "IntroduceSymbols" should {
    "match the correct implementation and check it at compile time" in {
      implicit val stringParser = new IntroduceSymbols.Parser[String] {
        override def parse(x: String): String = x
      }

      val endPoint = IntroduceSymbols.introduceTestOneArg((firstArg: String) =>
        (x: String) => firstArg + x + "hi"
      )

      endPoint("/some/path/haha!") must_== "some/some/path/haha!hi"
    }

    "match the correct implementation and check it at compile time given an Int argument" in {
      implicit val intParse = new IntroduceSymbols.Parser[Int] {
        override def parse(x: String): Int = Integer.parseInt(x)
      }

      val endPoint = IntroduceSymbols.introduceTestOneArg((firstArg: Int) =>
        (x: String) => firstArg.toString + x + "hi"
      )

      endPoint("/10/path/haha!") must_== "10/10/path/haha!hi"
    }

    "match the correct implementation and check it at compile time given an Int argument and then fail at runtime because a non int was supplied" in {
      implicit val intParse = new IntroduceSymbols.Parser[Int] {
        override def parse(x: String): Int = Integer.parseInt(x)
      }

      val endPoint = IntroduceSymbols.introduceTestOneArg((firstArg: Int) =>
        (x: String) => firstArg.toString + x + "hi"
      )

      endPoint("/derp/path/haha!") must throwA[NumberFormatException](message = """For input string: "derp"""")
    }
  }
}
