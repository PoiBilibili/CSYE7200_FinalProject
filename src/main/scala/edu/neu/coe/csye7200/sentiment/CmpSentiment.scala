package edu.neu.coe.csye7200.sentiment

import edu.neu.coe.csye7200.ingest.{Ingest, Keys, Response}
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import com.github.nscala_time.time.Imports._
import com.github.nscala_time.time._
import scala.io.{Codec, Source}

object CmpSentiment {

  def main(args: Array[String]): Unit ={
    print("Trump Score:"+CmpSentiment.calcSentimentFromSearchApi("Trump"))
    println("Sydney Score:"+CmpSentiment.calcSentimentFromSearchApi("Sydney"))
  }

  def cmpTwoString(k1:String="", k2:String=""): Boolean ={
    val result = calcSentimentFromSearchApi(k1) > calcSentimentFromSearchApi(k2)
    if(result) print(k1 + "'s score is higher than "+ k2 )
    else print(k2 + "'s score is higher than "+ k1 )
    result
  }
  def calcSentimentFromSearchApi(k: String = "", count: Int = 90, catchlog: Boolean = true): Double = {
    val ingester = new Ingest[Response]()
    implicit val codec = Codec.UTF8
    val source = Source.fromString(this.getFromSearchApiByKeyword(k.replaceAll(" ","%20"),count))
    val rts = for (t <- ingester(source).toSeq) yield t
    val rs = rts.flatMap(_.toOption)
    val tss = rs.map(r => r.statuses)
    //for (t <- tss) println(t.size)

    val ts = tss.flatten

    //println(ts.size)

    val sts = ts.par.map(s => SentimentUtils.detectSentimentScore(s.text,catchlog))

    val avgscore = sts.sum/sts.size
    //println(avgscore)

    avgscore
  }
  def getFromSearchApiByKeyword(k: String, count: Int = 90): String = {
    val today= DateTime.now
    //println(today.toString(StaticDateTimeFormat.forPattern("yyyy-MM-dd")))
    val ss = for (i <- 1 to 7) yield getFromSearchApiByKeywordForOneDay(today-i.days,k,count)
    ss.mkString("\n")
  }

  def getFromSearchApiByKeywordForOneDay(i: DateTime,k: String, count: Int): String = {
    val consumer = new CommonsHttpOAuthConsumer(Keys.consumerKey, Keys.consumerSecret)
    consumer.setTokenWithSecret(Keys.accessToken, Keys.accessTokenSecret)
    val url = "https://api.twitter.com/1.1/search/tweets.json?q=" + k + "&count=" + count + "&until=" + i.toString(StaticDateTimeFormat.forPattern("yyyy-MM-dd"))
    //println(url)
    val request = new HttpGet(url)
    consumer.sign(request)
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    IOUtils.toString(response.getEntity().getContent())
  }
}
