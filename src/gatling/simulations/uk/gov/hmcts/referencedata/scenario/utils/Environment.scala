package uk.gov.hmcts.jui.sscs.scenario.utils

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration

object Environment {
  val URL_TO_TEST = "https://jui-webapp-aat.service.core-compute-aat.internal"
  val IDAM_WEB_URL = "https://idam-web-public.aat.platform.hmcts.net"
  val CCD_WEB_URL = "https://ccd-case-management-web-aat.service.core-compute-aat.internal"
  val CCD_GATEWAY_URL = "https://gateway-ccd.aat.platform.hmcts.net"
  val BaseURL = "https://ccd-api-gateway-web-aat.service.core-compute-aat.internal"

  val minThinkTime = 5
  val maxThinkTime = 12
  val constantthinkTime = 7

  val HttpSSCSProtocol = http

  val commonSSCSHeader = Map(
    "Accept" -> "application/json, text/plain, */*",
    "Content-Type" -> "application/json",
    "Origin" -> URL_TO_TEST,
    "Pragma" -> "no-cache")

  val HttpCCDProtocol = http

  val jsoncommonHeader = Map(
    "Accept" -> "application/json",
    "Content-Type" -> "application/json",
    "Origin" -> CCD_WEB_URL)

  val idam_header = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3",
    "Accept-Encoding" -> "gzip, deflate, br",
    "Accept-Language" -> "en-US,en;q=0.9",
    "Origin" -> "https://idam-web-public.aat.platform.hmcts.net",
    "Upgrade-Insecure-Requests" -> "1")

  val commonHeader = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
    "Accept-Encoding" -> "gzip, deflate, br",
    "Accept-Language" -> "en-GB,en-US;q=0.9,en;q=0.8",
    "Connection" -> "keep-alive",
    "Upgrade-Insecure-Requests" -> "1",
    "User-Agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")

  val headers_0 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
    "Accept-Encoding" -> "gzip, deflate, br",
    "Accept-Language" -> "en-US,en;q=0.9",
    "Origin" -> IDAM_WEB_URL,
    "Upgrade-Insecure-Requests" -> "1")

  val headers_2 = Map("Accept" -> "application/json, text/plain, */*")

  val headers_3 = Map(
    "Access-Control-Request-Headers" -> "content-type,experimental",
    "Access-Control-Request-Method" -> "GET",
    "Origin" -> "https://ccd-case-management-web-aat.service.core-compute-aat.internal")

  val headers_4 = Map(
    "Access-Control-Request-Headers" -> "content-type",
    "Access-Control-Request-Method" -> "GET",
    "Origin" -> "https://ccd-case-management-web-aat.service.core-compute-aat.internal")

  val headers_5 = Map(
    "Accept" -> "application/json",
    "Content-Type" -> "application/json",
    "Origin" -> "https://ccd-case-management-web-aat.service.core-compute-aat.internal")

  val headers_6 = Map(
    "Access-Control-Request-Headers" -> "content-type",
    "Access-Control-Request-Method" -> "POST",
    "Origin" -> "https://ccd-case-management-web-aat.service.core-compute-aat.internal")

  val headers_9 = Map(
    "Accept" -> "application/json, text/plain, */*",
    "Content-Type" -> "application/json",
    "Origin" -> "https://jui-webapp-aat.service.core-compute-aat.internal")

  val headers_11 = Map(
    "Accept" -> "application/vnd.uk.gov.hmcts.ccd-data-store-api.ui-start-event-trigger.v2+json;charset=UTF-8",
    "Content-Type" -> "application/json",
    "Origin" -> "https://ccd-case-management-web-aat.service.core-compute-aat.internal",
    "experimental" -> "true")

  val headers_12 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3",
    "Accept-Encoding" -> "gzip, deflate, br",
    "Accept-Language" -> "en-US,en;q=0.9",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_1 = Map("Origin" -> URL_TO_TEST)
  //val headers_3 = Map("Accept" -> "application/json, text/plain, */*")
  //val headers_5 = Map("Accept" -> "*/*")

}