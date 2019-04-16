package edu.neu.coe.csye7200.retrieval

import edu.neu.coe.csye7200.sentiment.CmpSentiment
import org.apache.log4j.{Level, Logger}
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter._

/**
  *
  * created by JingZhou on 9th,Jan,2019
  *
  * reference:
  *
  * https://github.com/achoory/SparkStreaming_ExploringTwitterFeeds/blob/master/src/com/nus/sparkstreaming/PopularHashtags.scala
  * http://bahir.apache.org/docs/spark/current/spark-streaming-twitter/
  */

object PopularHashTags {
  def main(args: Array[String]): Unit ={
    runPopularHashTags()
  }

  def runPopularHashTags(k: String = ""): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    // create a SparkContext
    val sc = new SparkContext("local[*]", "PopularHashtags")

    // create up a Spark streaming context named "PopularHashtags" that runs locally using
    // all CPU cores and one-second batches of data
    val ssc = new StreamingContext(sc, Seconds(1))
    import edu.neu.coe.csye7200.ingest.Keys
    System.setProperty("twitter4j.oauth.consumerKey", Keys.consumerKey)
    System.setProperty("twitter4j.oauth.consumerSecret", Keys.consumerSecret)
    System.setProperty("twitter4j.oauth.accessToken", Keys.accessToken)
    System.setProperty("twitter4j.oauth.accessTokenSecret", Keys.accessTokenSecret)

    // create a DStream from Twitter using our streaming context
    val tweets = k match {
      case "" => TwitterUtils.createStream(ssc, None)
      case _  => TwitterUtils.createStream(ssc, None, Seq(k))
    }
    // extract the text of each status update into DStreams using map()
    val statuses = tweets.map(status => status.getText())

    // blow out each word into a new DStream
    val tweetwords = statuses.flatMap(tweetText => tweetText.split(" "))

    // eliminate anything that's not a hashtag
    val hashtags = tweetwords.filter(word => word.startsWith("#"))

    // map each hashtag to a key/value pair of (hashtag, 1) so we can count them up by adding up the values
    val hashtagKeyValues = hashtags.map(hashtag => (hashtag, 1))

    // count them up over a 5 minute window sliding every 30 second
    val hashtagCounts = hashtagKeyValues.reduceByKeyAndWindow((x, y) => x + y, (x, y) => x - y, Seconds(600), Seconds(1))
    //  You will often see this written in the following shorthand:
    //val hashtagCounts = hashtagKeyValues.reduceByKeyAndWindow( _ + _, _ -_, Seconds(300), Seconds(1))

    // sort the results by the count values
    val sortedResults = hashtagCounts.transform(rdd => rdd.sortBy(x => x._2, false))

    // print the top 1
    sortedResults.print
    val backup = sortedResults;
    sortedResults.repartition(1).saveAsTextFiles("resources/data/hashtags/popularhashtags")
    // set a checkpoint directory, and start
    ssc.checkpoint("test/checkpoint/")
    ssc.start()
    ssc.awaitTermination()
    sortedResults.repartition(1).saveAsTextFiles("resources/data/hashtags/popularhashtags")
  }
}
