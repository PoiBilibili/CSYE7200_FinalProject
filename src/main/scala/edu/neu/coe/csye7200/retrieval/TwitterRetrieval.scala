package edu.neu.coe.csye7200.retrieval

/**
  * @author JingZhou
  * created on 8th,Jan,2019
  *
  * reference:
  * https://developer.twitter.com/
  * http://twitter4j.org/en/
  * https://github.com/Twitter4J/Twitter4J/blob/master/twitter4j-examples/src/main/java/twitter4j/examples/search/SearchTweets.java
  * https://github.com/achoory/SparkStreaming_ExploringTwitterFeeds/blob/master/src/com/nus/sparkstreaming/PopularHashtags.scala
  *
  */

object TwitterRetrieval {


  def main(args: Array[String]) {
    PopularHashTags.runPopularHashTags()
  }
}
