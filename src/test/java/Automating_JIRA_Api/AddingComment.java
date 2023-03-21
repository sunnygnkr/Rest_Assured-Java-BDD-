package Automating_JIRA_Api;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import resources.ReUsableMethods;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AddingComment {

	@Test
	public static void addComment() {

		RestAssured.baseURI = "http://localhost:8181";

//		Login scenario code to get sessionId

		String key = given()
				.relaxedHTTPSValidation().
				header("Content-Type","application/json").
				body("{ \r\n"
						+ "    \"username\": \"sunnygnkr93\",\r\n"
						+ "    \"password\": \"Playboy@93\"\r\n"
						+ "}")
				.when().post("/rest/auth/1/session").then().extract().response().asString();

		JsonPath js = ReUsableMethods.rawToJson(key);
		String sessionId = js.get("session.value");
		System.out.println(sessionId);

//		Adding comment to the issue
		given().relaxedHTTPSValidation().log().all().pathParam("id", "10004").header("Content-Type", "application/json")
				.header("Cookie", "JSESSIONID="+sessionId+"")
				.body("{\r\n" + "    \"body\": \"Comment added through Rest Assured code.\",\r\n"
						+ "    \"visibility\": {\r\n" + "        \"type\": \"role\",\r\n"
						+ "        \"value\": \"Administrators\"\r\n" + "    }\r\n" + "}")
				.when().post("/rest/api/2/issue/{id}/comment").then().assertThat().statusCode(201).
				body("body", equalTo("Comment added through Rest Assured code.")).log().all();

	}

}
