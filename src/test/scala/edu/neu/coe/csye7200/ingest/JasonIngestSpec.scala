package edu.neu.coe.csye7200.ingest

import org.scalatest.{FlatSpec, Matchers}

import scala.io.{Codec, Source}
import scala.util._

/**
  * @author JingZhou
  * created on 8th,Jan,2019
  *
  * reference:
  * http://www.scalatest.org/at_a_glance/FlatSpec
  */
class JasonIngestSpec extends FlatSpec{

  behavior of "JasonIngest"

  override def withFixture(test: NoArgTest) = { // Define a shared fixture
    // Shared setup (run at beginning of each test)
    try test()
    finally {
      // Shared cleanup (run at end of each test)
    }
  }

  "The number of Tweets" should "have the size of 1" in {
    val ingester = new Ingest[JasonIngest]()
    implicit val codec = Codec.UTF8
    val sc = Source.fromFile("resources//json//tweet1.json")
    val tweets = for (t <- ingester(sc).toSeq) yield t
    assert(tweets.size == 1)
    sc.close()
  }

  "The pattern of Tweets" should "match pattern" in {
    val ingester = new Ingest[JasonIngest]()
    implicit val codec = Codec.UTF8
    val sc = Source.fromFile("resources//json//tweet1.json")
    val tweets = for (t <- ingester(sc).toSeq) yield t
    import org.scalatest.Matchers._
    tweets should matchPattern { case Stream(Success(_)) => }
    sc.close()
  }

  "The first Tweet" should "match the content" in {
    val ingester = new Ingest[JasonIngest]()
    implicit val codec = Codec.UTF8
    val sc = Source.fromFile("resources//json//tweet1.json")
    val tweets = for (t <- ingester(sc).toSeq) yield t
    val tw:JasonIngest = tweets.head match {
      case Success(x) => x
      case Failure(e) => throw new Exception("err:"+e)
    }
    assert(tw.retweet_count == 2301)
    assert(tw.created_at == "Tue Aug 23 13:53:11 +0000 2016")
    assert(tw.lang == "en")
    assert(tw.text == "It is being reported by virtually everyone, and is a fact, that the media pile on against me is the worst in American political history!")
    sc.close()
  }


}
