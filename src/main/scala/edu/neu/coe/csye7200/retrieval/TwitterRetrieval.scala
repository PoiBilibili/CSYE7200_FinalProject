package edu.neu.coe.csye7200.retrieval

/**
  * Created by JingZhou on 1st,Jan,2019
  *
  * reference:
  * https://developer.twitter.com/
  * https://danielasfregola.com/2015/12/07/twitter4s-a-scala-client-for-the-twitter-api/
  */

import com.danielasfregola.twitter4s.TwitterClient
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken}
import com.danielasfregola.twitter4s.entities.{HashTag, Tweet}


import scala.concurrent.ExecutionContext.Implicits.global

object TwitterRetrieval {
  val consumerToken = ConsumerToken(key = "thXpvQYh1HvLnhWvotbu8R0z0", secret = "XgfuZiTOo6bedNKCRtRQdcrup7XMQKLfqzcQakqU3kRPcMvCjb")
  val accessToken = AccessToken(key = "1082761043544281095-Y3lTN7qIZdSbepzTr66dLQdAY3IBP4", secret = "cQgUh91sqOsWXx4ORYNPKEsU0fKN6rK2nEtMKg9pvnuon")
  val client = new TwitterClient(consumerToken, accessToken)
  def getclinet = client
}

object UserTopHashtags extends App {

  def getTopHashtags(tweets: Seq[Tweet], n: Int = 10): Seq[(String, Int)] = {
    val hashtags: Seq[Seq[HashTag]] = tweets.map { tweet =>
      tweet.entities.map(_.hashtags).getOrElse(Seq.empty)
    }
    val hashtagTexts: Seq[String] = hashtags.flatten.map(_.text.toLowerCase)
    val hashtagFrequencies: Map[String, Int] = hashtagTexts.groupBy(identity).mapValues(_.size)
    hashtagFrequencies.toSeq.sortBy { case (entity, frequency) => -frequency }.take(n)
  }

  val client = TwitterRetrieval.getclinet

  val user = "iingwen"

  client.getUserTimelineForUser(screen_name = user, count = 200).map { tweets =>
    val topHashtags: Seq[((String, Int), Int)] = getTopHashtags(tweets).zipWithIndex
    val rankings = topHashtags.map { case ((entity, frequency), idx) => s"[${idx + 1}] $entity (found $frequency times)"}
    println(s"${user.toUpperCase}'S TOP HASHTAGS:")
    println(rankings.mkString("\n"))
  }

}

