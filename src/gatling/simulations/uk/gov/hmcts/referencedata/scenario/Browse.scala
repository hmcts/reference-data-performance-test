package uk.gov.hmcts.jui.sscs.scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration
import uk.gov.hmcts.jui.sscs.scenario.utils._

object Browse {

 val IdamJUIURL = scala.util.Properties.envOrElse("IDAM_WEB_URL", Environment.IDAM_WEB_URL).toLowerCase()
 val JUIBaseUrl = scala.util.Properties.envOrElse("URL_TO_TEST", Environment.URL_TO_TEST).toLowerCase()
 val feedASSCSJudgeData = csv("JUISSCSUser.csv").circular
 val MinThinkTime = Environment.minThinkTime
 val MaxThinkTime = Environment.maxThinkTime

 val landingLoginPage = exec(http("JUI_010_005_HomePage")
   .get("/"))

   .exec(http("JUI_010_010_HomePage")
     .get(IdamJUIURL + "/login?response_type=code&client_id=juiwebapp&redirect_uri="+JUIBaseUrl+"/oauth2/callback")
     .check(css("input[name='_csrf']", "value").saveAs("csrftoken"))
    /*.check(css(".form-group>input[name='upliftToken']", "value").saveAs("upliftToken"))
    .check(css(".form-group>input[name='response_type']", "value").saveAs("response_type"))
    .check(css(".form-group>input[name='redirect_uri']", "value").saveAs("redirect_uri"))
    .check(css(".form-group>input[name='client_id']", "value").saveAs("client_id"))
    .check(css(".form-group>input[name='scope']", "value").saveAs("scope"))
    .check(css(".form-group>input[name='state']", "value").saveAs("state"))
    .check(css(".form-group>input[name='continue']", "value").saveAs("continue"))*/)

   //Only required if the Case Create steps are NOT used
   .feed(feedASSCSJudgeData)

   .pause(MinThinkTime, MaxThinkTime)
}