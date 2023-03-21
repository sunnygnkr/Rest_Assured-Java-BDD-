package Dynamic_Payload_JSON;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import resources.Payload_Data;
import resources.ReUsableMethods;

public class Parameterizing_MultipleData {

	@DataProvider(name = "booksData")
	public static Object[][] multiData(){
		Object[][] obj = new Object[][] {{"sun","007"},{"sonu","008"},{"uju","009"}};
		
		return obj;
		
	}
	
	@Test(dataProvider = "booksData")
	public static void createBooks(String isbn, String isle) {
		RestAssured.baseURI = "http://216.10.245.166";

		String response = given().relaxedHTTPSValidation().header("Content-Type", "application/json")
				.body(Payload_Data.addBook(isbn,isle)).when().post("/Library/Addbook.php").then().assertThat().statusCode(200)
				.extract().response().asString();
		
		JsonPath jsonPath = ReUsableMethods.rawToJson(response);
		String bookId = jsonPath.getString("ID");
		System.out.println(bookId);
		
		given().relaxedHTTPSValidation().body(Payload_Data.deleteBook(bookId)).
		when().post("/Library/DeleteBook.php").
		then().assertThat().statusCode(200).log().all();
	}
	
}
