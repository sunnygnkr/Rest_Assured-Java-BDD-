package Dynamic_Payload_JSON;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import resources.Payload_Data;
import resources.ReUsableMethods;

@SuppressWarnings("unused")
public class DynamicPayload {

	@Test
	public void addBook() {

		RestAssured.baseURI = "http://216.10.245.166";

		String response = given().relaxedHTTPSValidation().header("Content-Type", "application/json")
				.body(Payload_Data.addBook("sun","007")).when().post("/Library/Addbook.php").then().assertThat().statusCode(200)
				.extract().response().asString();
		
	JsonPath js =	ReUsableMethods.rawToJson(response);
	String bookId = js.getString("ID");
	System.out.println("Book id: "+ bookId);

	given().relaxedHTTPSValidation().body(Payload_Data.deleteBook(bookId)).
	when().post("/Library/DeleteBook.php").
	then().assertThat().statusCode(200).log().all();
	
	}
	
}
