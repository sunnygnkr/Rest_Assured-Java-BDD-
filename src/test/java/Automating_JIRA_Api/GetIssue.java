package Automating_JIRA_Api;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

public class GetIssue {

	@Test
	public static void getIssueDetails() {

		RestAssured.baseURI = "http://localhost:8181";
		SessionFilter sessionFilter = new SessionFilter();

		String key = given()
				.relaxedHTTPSValidation().
				header("Content-Type","application/json").
				body("{ \r\n"
						+ "    \"username\": \"sunnygnkr93\",\r\n"
						+ "    \"password\": \"Playboy@93\"\r\n"
						+ "}").
				filter(sessionFilter).when().post("/rest/auth/1/session").then().extract().response().asString();

		
		String response = given().relaxedHTTPSValidation().filter(sessionFilter).pathParam("id", "10004")
				.queryParam("fields", "comment")
				.when().get("rest/api/2/issue/{id}").
				then().log().all().extract().response().asString();

//		System.out.println(response);
		
	}

}
