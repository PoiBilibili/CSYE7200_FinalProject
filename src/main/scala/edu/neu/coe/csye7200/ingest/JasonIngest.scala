package edu.neu.coe.csye7200.ingest

import scala.util._
import spray.json._

/**
  * @author JingZhou
  * created on 8th,Jan,2019
  *
  * reference:
  * https://github.com/spray/spray-json
  */

case class JasonIngest(text: String,lang: String,created_at: String,retweet_count: Int)

object JasonIngest extends App {

  object TweetProtocol extends DefaultJsonProtocol {
    implicit val formatTweet = jsonFormat4(JasonIngest.apply)
  }

  trait IngestibleTweet extends Ingestible[JasonIngest] {

    def fromString(w: String): Try[JasonIngest] = {
      println("w="+w.parseJson.prettyPrint)
      import TweetProtocol._
      Try(w.parseJson.convertTo[JasonIngest])
    }
  }

  implicit object IngestibleTweet extends IngestibleTweet

}
