package macros

import org.specs2.mutable.Specification

class IntroduceSymbolsUnitSpec extends Specification {
  "IntroduceSymbols" should {
    "match the correct implementation and check it at compile time" in {
      IntroduceSymbols.introduceTestOneArg((firstArg: String) =>
        (x: String) => firstArg + x + "hi"
      )("/some/path/haha!") must_== "some/some/path/haha!hi"
    }
  }
}
