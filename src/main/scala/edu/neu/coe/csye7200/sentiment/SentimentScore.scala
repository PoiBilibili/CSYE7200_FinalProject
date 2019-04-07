package edu.neu.coe.csye7200.sentiment

class SentimentScore {
  def calcSentimen(s: String = "", count: Int = 90, catchlog: Boolean = true): Double = {
    SentimentUtils.detectSentimentScore(s,catchlog)
  }
}
