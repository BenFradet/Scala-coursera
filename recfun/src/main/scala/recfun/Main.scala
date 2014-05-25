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

    if (c == 0) 
      1
    else if (c == r) 
      1
    else 
      pascal(c - 1, r-1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def isBalanced(tmpChars: List[Char], count: Int): Boolean = {
      if (count < 0) 
        false
      else if (tmpChars.isEmpty) 
        count == 0
      else {
        if (tmpChars.head == '(') 
          isBalanced(tmpChars.tail, count + 1)
        else if (tmpChars.head == ')')
          isBalanced(tmpChars.tail, count - 1)
        else
          isBalanced(tmpChars.tail, count)
      }
    }

    isBalanced(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def nestedCountChange(tmpMoney: Int, tmpCoins: List[Int], count: Int): Int = {
      if (tmpCoins.isEmpty || tmpMoney < 0) 
        0
      else {
        if (tmpMoney == 0) 
          count + 1
        else
          nestedCountChange(tmpMoney, tmpCoins.tail, count) + 
            nestedCountChange(tmpMoney - tmpCoins.head, tmpCoins, count)
      }
    }

    if (money < 0)
      throw new IllegalArgumentException("No negative amount")
    else if (money == 0)
      0
    else
      nestedCountChange(money, coins, 0)
  }
}
