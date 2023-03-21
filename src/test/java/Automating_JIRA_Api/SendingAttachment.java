package Automating_JIRA_Api;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import resources.ReUsableMethods;

import static io.restassured.RestAssured.*;

import java.io.File;

public class SendingAttachment {

	@Test
	public static void addComment() {

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

		JsonPath js = ReUsableMethods.rawToJson(key);
		String sessionId = js.get("session.value");
		System.out.println(sessionId);

//		Adding comment to the issue
		given().relaxedHTTPSValidation().log().all().pathParam("id", "10004").header("Content-Type", "application/json")
				.body("{\r\n" + "    \"body\": \"Comment to add attachment.\",\r\n"
						+ "    \"visibility\": {\r\n" + "        \"type\": \"role\",\r\n"
						+ "        \"value\": \"Administrators\"\r\n" + "    }\r\n" + "}").
				filter(sessionFilter)
				.when().post("/rest/api/2/issue/{id}/comment").then().assertThat().statusCode(201).
				log().all();

//		Adding attachment to the issue
		given().relaxedHTTPSValidation().log().all().header("X-Atlassian-Token", "no-check").
		filter(sessionFilter).pathParam("id", "10004").
		header("Content-type","multipart/form-data").
		multiPart("file",new File("data.txt")).
		when().post("/rest/api/2/issue/{id}/attachments").then().assertThat().statusCode(200);
	}

}
