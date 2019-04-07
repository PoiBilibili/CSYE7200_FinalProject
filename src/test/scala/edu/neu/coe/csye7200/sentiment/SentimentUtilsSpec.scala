package edu.neu.coe.csye7200.sentiment

import org.scalatest.{FlatSpec, Matchers}
/**
  * @author JingZhou
  * created on 8th,Jan,2019
  *
  * reference:
  * http://www.scalatest.org/at_a_glance/FlatSpec
  */
class SentimentUtilsSpec extends FlatSpec{

  behavior of "SentimentUtils"

  "Sentiment Score" should "be right" in {
    val senti = new SentimentScore()
    val score1 = senti.calcSentimen("I am feeling bad!");
    val score2 = senti.calcSentimen("I love this movie!")
    assert( score1 < score2)

    val score3 = senti.calcSentimen("I don't like it!");
    val score4 = senti.calcSentimen("I hate it!")
    assert( score3 > score4)

    val score5 = senti.calcSentimen("I like it!");
    val score6 = senti.calcSentimen("I love it!")
    assert(score5 < score6)
  }
}
