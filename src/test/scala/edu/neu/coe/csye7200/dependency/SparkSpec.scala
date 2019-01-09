package edu.neu.coe.csye7200.dependency

import org.scalatest.FlatSpec

class SparkSpec extends FlatSpec{
  "The dangerous place" should "be number 868" in{
    assert(SparkSample.sparkTestRun == 868)
  }
}
