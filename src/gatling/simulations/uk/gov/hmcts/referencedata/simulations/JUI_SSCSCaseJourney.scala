package uk.gov.hmcts.jui.sscs.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.session._
import uk.gov.hmcts.jui.sscs.scenario.CaseCreationPreReq
import uk.gov.hmcts.jui.sscs.scenario.utils._
import uk.gov.hmcts.jui.sscs.scenario._
import io.gatling.core.scenario.Simulation

import scala.concurrent.duration._

class JUI_SSCSCaseJourney extends Simulation {

  val JUIBaseUrl = scala.util.Properties.envOrElse("URL_TO_TEST", Environment.URL_TO_TEST).toLowerCase()

  val httpSSCSProtocol = Environment.HttpSSCSProtocol
    .baseUrl(JUIBaseUrl)
    .proxy(Proxy("proxyout.reform.hmcts.net", 8080).httpsPort(8080))
    .maxRedirects(10)
  // .disableAutoReferer

  val JUISSCSSCN = scenario("SCN_JUI_SSCSJourney")
    .exec(
      //Logout.logout,
      //CaseCreationPreReq.randNum,
      //CaseCreationPreReq.homepage,
      //CaseCreationPreReq.login,
      //CaseCreationPreReq.CaseCreate,
      //CaseCreationPreReq.DocumentUpload,
      //CaseCreationPreReq.AssignToJudge,
      Browse.landingLoginPage,
      JUILogin.submitLogin,
      JUICases.setCaseId, // Only required if amending existing cases
      JUICases.pickRandomCase,
      JUIDocument.openDocument,
      JUIDocument.AnnotateDocument, // Not currently working
      //JUIQuestion.sendQuestion,
      //JUIDecision.submitDecision,
      Logout.logout
    )

  setUp(JUISSCSSCN.inject(atOnceUsers(1))).protocols(httpSSCSProtocol)

}