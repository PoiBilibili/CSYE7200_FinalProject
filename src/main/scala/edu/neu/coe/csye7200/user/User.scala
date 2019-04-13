package edu.neu.coe.csye7200.user
import edu.neu.coe.csye7200.retrieval.PopularHashTags
import edu.neu.coe.csye7200.sentiment.CmpSentiment

object User {
  def main(args: Array[String]) {
    val inputstr = Console.readLine().split(" ")
    if (inputstr.size == 0) println("Invalid input!") else {
      inputstr(0) match {
        case "hashtags" => PopularHashTags.runPopularHashTags(inputstr(1))
        case "compare" => CmpSentiment.cmpTwoString(inputstr(1), inputstr(2))
        case _ => println("Invalid input.")
      }
    }
  }
}
