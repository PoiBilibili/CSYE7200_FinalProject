package edu.neu.coe.csye7200.dependency

import org.apache.spark.SparkContext

object SparkSample {
  def sparkTestRun()= {
    println("\nSparkSpec run\n")
    // create a SparkContext using every core of the local machine, named RatingsCounter
    val sc = new SparkContext("local[*]", "RatingsCounter")
    // load up each line of the ratings data into an RDD
    val lines = sc.textFile("resources/data/SacramentocrimeJanuary2006.csv")
    // convert each line to a string, split it out by tabs, and extract the third field.
    // (The file format is userID, movieID, rating, timestamp)
    val ratings = lines.map(x => x.toString().split(",")(2))
    // count up how many times each value (rating) occurs
    val results = ratings.countByValue()
    // sort the resulting map of (rating, count) tuples
    val sortedResults = results.toSeq.sortBy(_._1)
    // Print each result on its own line.
    sortedResults(0)._2
  }
}
