package macros

import org.specs2.mutable.Specification

class IntroduceSymbolsUnitSpec extends Specification {
  "IntroduceSymbols" should {
    "create a symbol in a simple function" in {
      IntroduceSymbols.introduceTest((x: String) => x + "hi")("test ") must_== "test hi"
    }
  }
}
