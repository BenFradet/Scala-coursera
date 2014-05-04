package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c < 0 || r < 0)
      throw new IllegalArgumentException("Pascal's triangle doesn't" +
        " work with negative numbers");
    else if (c > r)
      throw new IllegalArgumentException("The column value can't be higher" + 
        " than the row value");

    if (c == 0) 1
    else if (c == r) 1
    else pascal(c - 1, r-1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = ???

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = ???
}
