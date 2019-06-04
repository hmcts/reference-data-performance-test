package uk.gov.hmcts.jui.sscs.scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.jui.sscs.scenario.utils._

object Logout {
  val IdamJUIURL = scala.util.Properties.envOrElse("IDAM_WEB_URL", Environment.IDAM_WEB_URL).toLowerCase()
  val MinThinkTime = Environment.minThinkTime
  val MaxThinkTime = Environment.maxThinkTime
  val JUIBaseUrl = scala.util.Properties.envOrElse("URL_TO_TEST", Environment.URL_TO_TEST).toLowerCase()

  val logout = exec(http("JUI_260_Logout")
    .get(IdamJUIURL + "/login?response_type=code&client_id=juiwebapp&redirect_uri=" + JUIBaseUrl + "/oauth2/callback"))
  //.pause(MinThinkTime, MaxThinkTime)
  /*.exec(http("TX50_JUI_Logout_govuk-template.js")
    .get(IdamJUIURL + "/public/javascripts/govuk-template.js?0.23.0")
    .resources(http("TX50_JUI_Logout_govuk-template-print.css")
      .get(IdamJUIURL + "/public/stylesheets/govuk-template-print.css?0.23.0"),
      http("TX50_JUI_Logout_gov.uk_logotype_crown_invert_trans.png")
        .get(IdamJUIURL + "/public/images/gov.uk_logotype_crown_invert_trans.png?0.23.0"),
      http("TX50_JUI_Logout_govuk-template.css")
        .get(IdamJUIURL + "/public/stylesheets/govuk-template.css?0.23.0"),
      http("TX50_JUI_Logout_fonts.css")
        .get(IdamJUIURL + "/public/stylesheets/fonts.css?0.23.0"),
      http("TX50_JUI_Logout_open-government-licence_2x.png")
        .get(IdamJUIURL + "/public/stylesheets/images/open-government-licence_2x.png?0.23.0"),
      http("TX50_JUI_Logout_govuk-crest-2x.png")
        .get(IdamJUIURL + "/public/stylesheets/images/govuk-crest-2x.png?0.23.0"),
      http("TX50_JUI_Logout_gov.uk_logotype_crown.png")
        .get(IdamJUIURL + "/public/stylesheets/images/gov.uk_logotype_crown.png?0.23.0")))
        */
}