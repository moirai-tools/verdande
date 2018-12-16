package org.verdande.core

import org.scalatest.{FlatSpec, Matchers, Outcome}

class GaugeTest extends FlatSpec with Matchers {

  behavior of "Gauge"

  trait Setup {
    val gauge = Gauge.build(
      name = "example",
      description = "Example gauge for tests"
    )

    def shouldHaveOnlyOneSeries(f: Series => Unit): Unit = {
      val result = gauge.collect()
      result.series should have size 1
      val series = result.series.head
      f(series)
    }

  }

  it should "start form 0" in new Setup {
    val result = gauge.collect()
    shouldHaveOnlyOneSeries { series =>
      series.value shouldEqual 0.0
    }
  }

  it should "allow increment by one" in new Setup {
    gauge.inc()
    shouldHaveOnlyOneSeries { series =>
      series.value shouldEqual 1.0
    }
  }
}