package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s1001 = singletonSet(1001)
    val emptySet = intersect(s1, s2)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }
  
  test("union of two empty sets is an empty set") {
    new TestSets {
      val s = union(emptySet, emptySet)
      assert(!contains(s, 1))
      assert(!contains(s, 2))
      assert(!contains(s, 3))
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains no elements if the sets are disjoints" +
    " {1}\u2229{2}={}") {
      new TestSets {
        val s = intersect(s1, s2)
        assert(!contains(s, 1), "Intersect 1")
        assert(!contains(s, 2), "Intersect 2")
      }
    }

  test("intersect contains one element if the sets have one common element" +
    " {1,2}\u2229{1}={1}") {
      new TestSets {
        val s = intersect(union(s1, s2), s1)
        assert(contains(s, 1), "Intersect 1")
        assert(!contains(s, 2), "Intersect 2")
      }
    }

  test("intersection of 2 empty sets is an empty set") {
    new TestSets {
      val s = intersect(emptySet, emptySet)
      assert(!contains(s, 1))
      assert(!contains(s, 2))
      assert(!contains(s, 3))
    }
  }

  test("diff contains no elements if diff of the two same sets {1}-{1} = {}") {
    new TestSets {
      val s = diff(s1, s1)
      assert(!contains(s, 1), "Diff 1")
    }
  }

  test("diff contains all elements of first parameters if the two sets are" +
    " disjoints {1} - {2} = {1}") {
      new TestSets {
        val s = diff(s1, s2)
        assert(contains(s, 1), "Diff 1")
        assert(!contains(s, 2), "Diff 2")
      }
    }

  test("diff contains the difference of the two sets {1, 2} - {2} = {1}") {
    new TestSets {
      val s = diff(union(s1, s2), s2)
      assert(contains(s, 1), "Diff 1")
      assert(!contains(s, 2), "Diff 2")
    }
  }

  test("diff of two empty sets is an empty set") {
    new TestSets {
      val s = diff(emptySet, emptySet)
      assert(!contains(s, 1))
      assert(!contains(s, 2))
      assert(!contains(s, 3))
    }
  }

  test("filter is empty if no member satisfies the predicate") {
    new TestSets {
      val s = filter(s1, x => x % 2 == 0)
      assert(!contains(s, 1), "Filter 1")
    }
  }

  test("filter filters correctly x % 2 == 0 for {1, 2, 3}") {
    new TestSets {
      val s = filter(union(union(s1, s2), s3), x => x % 2 == 0)
      assert(!contains(s, 1), "Filter 1")
      assert(contains(s, 2), "Filter 2")
      assert(!contains(s, 3), "Filter 3")
    }
  }

  test("forall returns false for x % 2 == 0 for {1, 2, 3}") {
    new TestSets {
      assert(!forall(union(union(s1, s2), s3), x => x % 2 == 0))
    }
  }

  test("forall returns true for x % 2 != 0 for {1, 3}") {
    new TestSets {
      assert(forall(union(s1, s3), x => x % 2 != 0))
    }
  }

  test("forall doesnt take into account values out of bounds") {
    new TestSets {
      assert(forall(s1001, x => x % 2 == 0))
    }
  }

  test("exists returns true for x % 2 == 0 for {1, 2, 3}") {
    new TestSets {
      assert(exists(union(union(s1, s2), s3), x => x % 2 == 0))
    }
  }

  test("exists returns false for x % 2 == 0 for {1, 3}") {
    new TestSets {
      assert(!exists(union(s1, s3), x => x % 2 == 0))
    }
  }

  test("exists doesnt take into account values out of bounds") {
    new TestSets {
      assert(!exists(s1001, x => x % 2 == 0))
    }
  }

  test("map returns {2,4,6} being given {1,2,3} and x => x * 2 as parameters") {
    new TestSets {
      val s = map(union(union(s1, s2), s3), x => x * 2)
      assert(contains(s, 2))
      assert(contains(s, 4))
      assert(contains(s, 6))
      assert(!contains(s, 1))
      assert(!contains(s, 3))
    }
  }
}
