package uk.gov.hmcts.jui.sscs.scenario

//import java.sql.Date
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.text.SimpleDateFormat
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random
import uk.gov.hmcts.jui.sscs.scenario.utils._
import uk.gov.hmcts.jui.sscs.simulations.checks.{CsrfCheck, CurrentPageUrl}

object CaseCreationPreReq {

  val idamURL = scala.util.Properties.envOrElse("IDAM_WEB_URL", Environment.IDAM_WEB_URL).toLowerCase()
  val CCDBaseUrl = scala.util.Properties.envOrElse("CCD_URL", Environment.CCD_WEB_URL).toLowerCase()
  val CCD_URL = scala.util.Properties.envOrElse("CCD_Web_URL", Environment.CCD_WEB_URL).toLowerCase()
  val CCDGatewayUrl = scala.util.Properties.envOrElse("CCD_Gateway_URL", Environment.CCD_GATEWAY_URL).toLowerCase()
  val BaseURL = scala.util.Properties.envOrElse("BaseURL", Environment.BaseURL).toLowerCase()
  val feedASSCSJudgeData = csv("JUISSCSUser.csv").circular
  val MinThinkTime = Environment.minThinkTime
  val MaxThinkTime = Environment.maxThinkTime

  val headers_0 = Environment.headers_0
  val headers_2 = Environment.headers_2
  val headers_4 = Environment.headers_4
  val headers_5 = Environment.headers_5
  val headers_6 = Environment.headers_6
  val headers_11 = Environment.headers_11
  val headers_12 = Environment.headers_12
  val idam_header = Environment.idam_header
  val CommonHeader = Environment.commonHeader
  val jsoncommonHeader = Environment.jsoncommonHeader

  private val rng: Random = new Random()
  private def number(): Int = rng.nextInt(9999)

  val randNum = exec(_.setAll(
    ("caseNumber", number())
  ))

  //println(DateTimeFormatter.ofPattern("YYYYMMdd").format(java.time.LocalDate.now))
  //println(java.time.LocalDate.now)

    //.exec(_.set("dateNow", java.time.LocalDate.now))

  //println("${dateNow}")

  val sdfDate = new SimpleDateFormat("ddMMyy")
  val now = new Date()
  val timeStamp = sdfDate.format(now)


  //exec(_.set("dateNow", sdfDate))

  //println("${dateNow}")


  val homepage = exec(http("PR_JUI_010_005_HomePage")
      .get(CCD_URL + "/")
        .headers(CommonHeader))

    //.pause(MinThinkTime, MaxThinkTime)

    .exec(http("PR_JUI_010_010_HomePage")
      .get(idamURL + "/login?response_type=code&client_id=ccd_gateway&redirect_uri=https%3A%2F%2Fccd-case-management-web-aat.service.core-compute-aat.internal%2Foauth2redirect")
        .headers(headers_12)
        .check(CurrentPageUrl.save)
        .check(CsrfCheck.save))

    .feed(feedASSCSJudgeData)

    .pause(MinThinkTime, MaxThinkTime)

  val login = exec(http("PR_JUI_020_005_Login")
      //.post(IdamCCDURL + "/login?response_type=code&client_id=ccd_gateway&redirect_uri=" + CCDBaseUrl + "/oauth2redirect")
      .post(idamURL + "/login?response_type=code&client_id=ccd_gateway&redirect_uri=https%3A%2F%2Fccd-case-management-web-aat.service.core-compute-aat.internal%2Foauth2redirect")
      .disableFollowRedirect
      .headers(idam_header)
      .formParam("username", "juitestuser2@gmail.com")
      .formParam("password", "Monday01")
      .formParam("save", "Sign in")
      .formParam("selfRegistrationEnabled", "false")
      .formParam("_csrf", "${csrf}")
      //.check(regex("oauth2redirect?code=(.*)&scope").saveAs("authCode"))
      .check(headerRegex("Location", "(?<=code=)(.*)&scope").saveAs("authCode"))
      .check(status.in(200,302))

      .resources(http("PR_JUI_020_010_Login")
        .get(CCD_URL + "/config")
        .headers(headers_2)))

    .exec(http("PR_JUI_020_015_Login")
      .options(BaseURL + "/oauth2?code=${authCode}&redirect_uri=https://ccd-case-management-web-aat.service.core-compute-aat.internal/oauth2redirect")
      .headers(headers_4))
    .exec(http("PR_JUI_020_020_Login")
      .get(BaseURL + "/oauth2?code=${authCode}&redirect_uri=https://ccd-case-management-web-aat.service.core-compute-aat.internal/oauth2redirect")
      .headers(headers_5))

    .exec(http("PR_JUI_020_025_Login")
      .get(CCD_URL + "/config")
      .headers(headers_2))

    .exec(http("PR_JUI_020_030_Login")
      .options(BaseURL + "/data/caseworkers/:uid/profile"))

    .exec(http("PR_JUI_020_035_Login")
      .get(BaseURL + "/data/caseworkers/:uid/profile")
      .headers(jsoncommonHeader))

    .exec(http("PR_JUI_020_040_Login")
      .options(BaseURL + "/aggregated/caseworkers/:uid/jurisdictions/SSCS/case-types?access=read")
      .resources(http("PR_JUI_020_045_Login")
        .get(BaseURL + "/aggregated/caseworkers/:uid/jurisdictions/SSCS/case-types?access=read")
        .headers(jsoncommonHeader)))

    .exec(http("PR_JUI_020_050_Login")
      .options(BaseURL + "/aggregated/caseworkers/:uid/jurisdictions/SSCS/case-types/Benefit/work-basket-inputs"))

    .exec(http("PR_JUI_020_055_Login")
      .options(BaseURL + "/aggregated/caseworkers/:uid/jurisdictions/SSCS/case-types/Benefit/cases?view=WORKBASKET&state=TODO&page=1"))

    .exec(http("PR_JUI_020_060_Login")
      .options(BaseURL + "/data/caseworkers/:uid/jurisdictions/SSCS/case-types/Benefit/cases/pagination_metadata?state=TODO"))
    .exec(http("PR_JUI_020_065_Login")
      .get(BaseURL + "/aggregated/caseworkers/:uid/jurisdictions/SSCS/case-types/Benefit/work-basket-inputs")
      .headers(jsoncommonHeader))

    .exec(http("PR_JUI_020_070_Login")
      .get(BaseURL + "/aggregated/caseworkers/:uid/jurisdictions/SSCS/case-types/Benefit/cases?view=WORKBASKET&state=TODO&page=1")
      .headers(jsoncommonHeader))

    .exec(http("PR_JUI_020_075_Login")
      .get(BaseURL + "/data/caseworkers/:uid/jurisdictions/SSCS/case-types/Benefit/cases/pagination_metadata?state=TODO")
      .headers(jsoncommonHeader))

    .pause(MinThinkTime, MaxThinkTime)

  val CaseCreate = exec(_.set("dateNow", timeStamp))

    .exec(http("PR_JUI_030_005_CreateCaseLandingPage")
      .options(BaseURL + "/aggregated/caseworkers/:uid/jurisdictions/SSCS/case-types?access=create")
    .resources(http("PR_JUI_030_010_CreateCaseLandingPage")
      .get(BaseURL + "/aggregated/caseworkers/:uid/jurisdictions/SSCS/case-types?access=create")
      .headers(jsoncommonHeader)))
    .pause(3)

    .exec(http("PR_JUI_030_015_CreateCaseLandingPage")
      .options(BaseURL + "/aggregated/caseworkers/:uid/jurisdictions/SSCS/case-types/Benefit/event-triggers/appealCreated?ignore-warning=false")
      .resources(http("PR_JUI_030_020_CreateCaseLandingPage")
        .get(BaseURL + "/aggregated/caseworkers/:uid/jurisdictions/SSCS/case-types/Benefit/event-triggers/appealCreated?ignore-warning=false")
        .headers(jsoncommonHeader)))

    .exec(http("PR_JUI_030_025_CreateCaseLandingPage")
        .get(BaseURL + "/aggregated/caseworkers/:uid/jurisdictions/SSCS/case-types/Benefit/event-triggers/appealCreated?ignore-warning=false")
        .check(jsonPath("$.event_token").saveAs("New_Case_event_token"))
        .headers(jsoncommonHeader))

    .pause(MinThinkTime, MaxThinkTime)

    .exec(http("PR_JUI_040_005_CreateCase")
      .post(BaseURL + "/data/caseworkers/:uid/jurisdictions/SSCS/case-types/Benefit/cases?ignore-warning=false")
        .headers(jsoncommonHeader)
        .body(StringBody("{\n  \"data\": {\n    \"caseReference\": \"PT${dateNow}_${caseNumber}\",\n    \"caseCreated\": null,\n    \"region\": null,\n    \"appeal\": {\n      \"receivedVia\": null,\n      \"mrnDetails\": {\n        \"dwpIssuingOffice\": null,\n        \"mrnDate\": null,\n        \"mrnLateReason\": null,\n        \"mrnMissingReason\": null\n      },\n      \"appellant\": {\n        \"name\": {\n          \"title\": \"Mr\",\n          \"firstName\": \"John\",\n          \"middleName\": null,\n          \"lastName\": \"Smith\"\n        },\n        \"identity\": {\n          \"dob\": \"1960-03-02\",\n          \"nino\": null\n        },\n        \"address\": {\n          \"line1\": null,\n          \"line2\": null,\n          \"line3\": null,\n          \"town\": null,\n          \"county\": null,\n          \"postcode\": null,\n          \"country\": null\n        },\n        \"contact\": {\n          \"phone\": null,\n          \"mobile\": null,\n          \"email\": null\n        },\n        \"isAppointee\": null,\n        \"appointee\": {\n          \"name\": {\n            \"title\": null,\n            \"firstName\": null,\n            \"middleName\": null,\n            \"lastName\": null\n          },\n          \"identity\": {\n            \"dob\": null,\n            \"nino\": null\n          },\n          \"address\": {\n            \"line1\": null,\n            \"line2\": null,\n            \"line3\": null,\n            \"town\": null,\n            \"county\": null,\n            \"postcode\": null,\n            \"country\": null\n          },\n          \"contact\": {\n            \"phone\": null,\n            \"mobile\": null,\n            \"email\": null\n          }\n        },\n        \"isAddressSameAsAppointee\": null\n      },\n      \"benefitType\": {\n        \"code\": \"PIP\",\n        \"description\": \"Benefit\"\n      },\n      \"hearingType\": \"cor\",\n      \"hearingOptions\": {\n        \"wantsToAttend\": null,\n        \"languageInterpreter\": null,\n        \"other\": null,\n        \"signLanguageType\": null\n      },\n      \"appealReasons\": {\n        \"reasons\": [],\n        \"otherReasons\": null\n      },\n      \"supporter\": {\n        \"name\": {\n          \"title\": null,\n          \"firstName\": null,\n          \"middleName\": null,\n          \"lastName\": null\n        },\n        \"contact\": {\n          \"phone\": null,\n          \"mobile\": null,\n          \"email\": null\n        }\n      },\n      \"rep\": {\n        \"hasRepresentative\": null\n      },\n      \"signer\": null\n    },\n    \"regionalProcessingCenter\": {\n      \"name\": null,\n      \"address1\": null,\n      \"address2\": null,\n      \"address3\": null,\n      \"address4\": null,\n      \"postcode\": null,\n      \"city\": null,\n      \"phoneNumber\": null,\n      \"faxNumber\": null\n    },\n    \"panel\": {\n      \"assignedTo\": null,\n      \"medicalMember\": null,\n      \"disabilityQualifiedMember\": null\n    }\n  },\n  \"event\": {\n    \"id\": \"appealCreated\",\n    \"summary\": \"\",\n    \"description\": \"\"\n  },\n  \"event_token\": \"${New_Case_event_token}\",\n  \"ignore_warning\": false,\n  \"event_data\": {\n    \"caseReference\": \"PT20190423_JM_002\",\n    \"caseCreated\": null,\n    \"region\": null,\n    \"appeal\": {\n      \"receivedVia\": null,\n      \"mrnDetails\": {\n        \"dwpIssuingOffice\": null,\n        \"mrnDate\": null,\n        \"mrnLateReason\": null,\n        \"mrnMissingReason\": null\n      },\n      \"appellant\": {\n        \"name\": {\n          \"title\": \"Mr\",\n          \"firstName\": \"John\",\n          \"middleName\": null,\n          \"lastName\": \"Smith\"\n        },\n        \"identity\": {\n          \"dob\": \"1960-03-02\",\n          \"nino\": null\n        },\n        \"address\": {\n          \"line1\": null,\n          \"line2\": null,\n          \"line3\": null,\n          \"town\": null,\n          \"county\": null,\n          \"postcode\": null,\n          \"country\": null\n        },\n        \"contact\": {\n          \"phone\": null,\n          \"mobile\": null,\n          \"email\": null\n        },\n        \"isAppointee\": null,\n        \"appointee\": {\n          \"name\": {\n            \"title\": null,\n            \"firstName\": null,\n            \"middleName\": null,\n            \"lastName\": null\n          },\n          \"identity\": {\n            \"dob\": null,\n            \"nino\": null\n          },\n          \"address\": {\n            \"line1\": null,\n            \"line2\": null,\n            \"line3\": null,\n            \"town\": null,\n            \"county\": null,\n            \"postcode\": null,\n            \"country\": null\n          },\n          \"contact\": {\n            \"phone\": null,\n            \"mobile\": null,\n            \"email\": null\n          }\n        },\n        \"isAddressSameAsAppointee\": null\n      },\n      \"benefitType\": {\n        \"code\": \"PIP\",\n        \"description\": \"Benefit\"\n      },\n      \"hearingType\": \"cor\",\n      \"hearingOptions\": {\n        \"wantsToAttend\": null,\n        \"languageInterpreter\": null,\n        \"other\": null,\n        \"signLanguageType\": null\n      },\n      \"appealReasons\": {\n        \"reasons\": [],\n        \"otherReasons\": null\n      },\n      \"supporter\": {\n        \"name\": {\n          \"title\": null,\n          \"firstName\": null,\n          \"middleName\": null,\n          \"lastName\": null\n        },\n        \"contact\": {\n          \"phone\": null,\n          \"mobile\": null,\n          \"email\": null\n        }\n      },\n      \"rep\": {\n        \"hasRepresentative\": null\n      },\n      \"signer\": null\n    },\n    \"regionalProcessingCenter\": {\n      \"name\": null,\n      \"address1\": null,\n      \"address2\": null,\n      \"address3\": null,\n      \"address4\": null,\n      \"postcode\": null,\n      \"city\": null,\n      \"phoneNumber\": null,\n      \"faxNumber\": null\n    },\n    \"panel\": {\n      \"assignedTo\": null,\n      \"medicalMember\": null,\n      \"disabilityQualifiedMember\": null\n    }\n  }\n}"))
        .check(jsonPath("$.id").saveAs("New_Case_Id")))

    .exec(http("PR_JUI_040_010_CreateCase")
      .post(BaseURL + "/data/caseworkers/:uid/jurisdictions/SSCS/case-types/Benefit/cases?ignore-warning=false")
        .headers(jsoncommonHeader)
        .body(StringBody("{\n  \"data\": {\n    \"caseReference\": \"PT${dateNow}_${caseNumber}\",\n    \"caseCreated\": null,\n    \"region\": null,\n    \"appeal\": {\n      \"receivedVia\": null,\n      \"mrnDetails\": {\n        \"dwpIssuingOffice\": null,\n        \"mrnDate\": null,\n        \"mrnLateReason\": null,\n        \"mrnMissingReason\": null\n      },\n      \"appellant\": {\n        \"name\": {\n          \"title\": \"Mr\",\n          \"firstName\": \"John\",\n          \"middleName\": null,\n          \"lastName\": \"Smith\"\n        },\n        \"identity\": {\n          \"dob\": \"1960-03-02\",\n          \"nino\": null\n        },\n        \"address\": {\n          \"line1\": null,\n          \"line2\": null,\n          \"line3\": null,\n          \"town\": null,\n          \"county\": null,\n          \"postcode\": null,\n          \"country\": null\n        },\n        \"contact\": {\n          \"phone\": null,\n          \"mobile\": null,\n          \"email\": null\n        },\n        \"isAppointee\": null,\n        \"appointee\": {\n          \"name\": {\n            \"title\": null,\n            \"firstName\": null,\n            \"middleName\": null,\n            \"lastName\": null\n          },\n          \"identity\": {\n            \"dob\": null,\n            \"nino\": null\n          },\n          \"address\": {\n            \"line1\": null,\n            \"line2\": null,\n            \"line3\": null,\n            \"town\": null,\n            \"county\": null,\n            \"postcode\": null,\n            \"country\": null\n          },\n          \"contact\": {\n            \"phone\": null,\n            \"mobile\": null,\n            \"email\": null\n          }\n        },\n        \"isAddressSameAsAppointee\": null\n      },\n      \"benefitType\": {\n        \"code\": \"PIP\",\n        \"description\": \"Benefit\"\n      },\n      \"hearingType\": \"cor\",\n      \"hearingOptions\": {\n        \"wantsToAttend\": null,\n        \"languageInterpreter\": null,\n        \"other\": null,\n        \"signLanguageType\": null\n      },\n      \"appealReasons\": {\n        \"reasons\": [],\n        \"otherReasons\": null\n      },\n      \"supporter\": {\n        \"name\": {\n          \"title\": null,\n          \"firstName\": null,\n          \"middleName\": null,\n          \"lastName\": null\n        },\n        \"contact\": {\n          \"phone\": null,\n          \"mobile\": null,\n          \"email\": null\n        }\n      },\n      \"rep\": {\n        \"hasRepresentative\": null\n      },\n      \"signer\": null\n    },\n    \"regionalProcessingCenter\": {\n      \"name\": null,\n      \"address1\": null,\n      \"address2\": null,\n      \"address3\": null,\n      \"address4\": null,\n      \"postcode\": null,\n      \"city\": null,\n      \"phoneNumber\": null,\n      \"faxNumber\": null\n    },\n    \"panel\": {\n      \"assignedTo\": null,\n      \"medicalMember\": null,\n      \"disabilityQualifiedMember\": null\n    }\n  },\n  \"event\": {\n    \"id\": \"appealCreated\",\n    \"summary\": \"\",\n    \"description\": \"\"\n  },\n  \"event_token\": \"${New_Case_event_token}\",\n  \"ignore_warning\": false,\n  \"draft_id\": null\n}"))
        .check(jsonPath("$.id").saveAs("New_Case_Id")))

    .pause(MinThinkTime, MaxThinkTime)

  val DocumentUpload = exec(http("PR_JUI_050_005_DocumentUpload")
    .get(BaseURL + "/data/internal/cases/${New_Case_Id}/event-triggers/uploadDocument?ignore-warning=false")
      .headers(headers_11)
      .check(jsonPath("$.event_token").saveAs("existing_case_event_token")))

    .pause(MinThinkTime, MaxThinkTime)

    .exec(http("PR_JUI_050_010_DocumentUpload")
      .post(BaseURL + "/documents")
        .bodyPart(RawFileBodyPart("files", "1111004.pdf")
          .fileName("1111004.pdf")
          .transferEncoding("binary")
        ).asMultipartForm
        .formParam("classification", "PUBLIC")
        .check(status.is(200))
        .check(regex("""http://(.+)/""").saveAs("DMURL"))
        .check(regex("""/documents/(.+)"""").saveAs("Document_ID")))

    .exec(http("PR_JUI_050_015_DocumentUpload")
      .options(BaseURL + "/data/caseworkers/:uid/jurisdictions/SSCS/case-types/Benefit/validate"))

    .exec(http("PR_JUI_050_020_DocumentUpload")
      .post(BaseURL +"/data/caseworkers/:uid/jurisdictions/SSCS/case-types/Benefit/cases/${New_Case_Id}/events")
        .headers(jsoncommonHeader)
        .body(StringBody("{\n  \"data\": {\n    \"sscsDocument\": [\n      {\n        \"id\": null,\n        \"value\": {\n          \"documentType\": \"appellantEvidence\",\n          \"documentEmailContent\": null,\n          \"documentDateAdded\": null,\n          \"documentComment\": null,\n          \"documentFileName\": null,\n          \"documentLink\": {\n            \"document_url\": \"http://dm-store-aat.service.core-compute-aat.internal:443/documents/${Document_ID}\",\n            \"document_binary_url\": \"http://dm-store-aat.service.core-compute-aat.internal:443/documents/${Document_ID}/binary\",\n            \"document_filename\": \"1111004.pdf\"\n          }\n        }\n      }\n    ]\n  },\n  \"event\": {\n    \"id\": \"uploadDocument\",\n    \"summary\": \"\",\n    \"description\": \"\"\n  },\n  \"event_token\": \"${existing_case_event_token}\",\n  \"ignore_warning\": false\n}")))

    .pause(MinThinkTime, MaxThinkTime)

  val AssignToJudge = exec(http("PR_JUI_050_005_AssignToJudge")
    .get(BaseURL + "/data/internal/cases/${New_Case_Id}/event-triggers/assignToJudge?ignore-warning=false")
      .headers(headers_11)
      .check(jsonPath("$.event_token").saveAs("existing_case_event_token")))

    .pause(MinThinkTime, MaxThinkTime)

    .exec(http("PR_JUI_050_010_AssignToJudge")
      .post(BaseURL + "/data/caseworkers/:uid/jurisdictions/SSCS/case-types/Benefit/cases/${New_Case_Id}/events")
        .headers(jsoncommonHeader)
        .body(StringBody("{\n  \"data\": {\n    \"assignedToJudge\": \"${SSCSUserName}\",\n    \"assignedToMedicalMember\": \"510604|Medical 1\",\n    \"assignedToDisabilityMember\": \"511043|Disability 1\"\n  },\n  \"event\": {\n    \"id\": \"assignToJudge\",\n    \"summary\": \"\",\n    \"description\": \"\"\n  },\n  \"event_token\": \"${existing_case_event_token}\",\n  \"ignore_warning\": false,\n  \"event_data\": {\n    \"assignedToJudge\": \"${SSCSUserName}\",\n    \"assignedToMedicalMember\": \"510604|Medical 1\",\n    \"assignedToDisabilityMember\": \"511043|Disability 1\"\n  },\n  \"case_reference\": \"${New_Case_Id}\"\n}")))

    .pause(MinThinkTime, MaxThinkTime)
}
