package uk.gov.hmcts.jui.sscs.simulations.checks

import io.gatling.core.Predef._
import io.gatling.core.check.CheckBuilder
import io.gatling.core.check.extractor.css.CssCheckType
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck
import io.gatling.http.check.url.CurrentLocationCheckType
import io.gatling.http.response.Response
import jodd.lagarto.dom.NodeSelector

object CurrentPageUrl {
  def save: CheckBuilder[CurrentLocationCheckType,String,String] = currentLocation.saveAs("currentPageUrl")
  def currentPageTemplate: String = "${currentPageUrl}"
}
