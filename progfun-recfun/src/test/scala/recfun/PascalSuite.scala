package recfun

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PascalSuite extends FunSuite {
  import Main.pascal

  test("pascal throws an exception with negative numbers") {
    intercept[IllegalArgumentException] {
      pascal(-2, -3)
    }
  }

  test("pascal throws an exception if c > r") {
    intercept[IllegalArgumentException] {
      pascal(10, 2)
    }
  }

  test("pascal: col=0,row=2") {
    assert(pascal(0,2) === 1)
  }

  test("pascal: col=1,row=2") {
    assert(pascal(1,2) === 2)
  }

  test("pascal: col=1,row=3") {
    assert(pascal(1,3) === 3)
  }
}
