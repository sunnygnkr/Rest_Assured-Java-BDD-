package Test_Package1;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import resources.Payload_Data;
import resources.ReUsableMethods;

public class ReadData_JSONFile {

	@Test
	public static void createBook() throws IOException {

		RestAssured.baseURI = "http://216.10.245.166";

		String response = given().relaxedHTTPSValidation().header("Content-Type", "application/json").
				
//				Since data from file is in byte format we need to convert it to String using typecasting
				body(new String(Files.readAllBytes(Paths
						.get("C:/Users/QQ864CK/eclipse-workspace/API_Automation/src/test/java/resources/data.json"))))
				
				.when().post("/Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract()
				.response().asString();

		JsonPath js = ReUsableMethods.rawToJson(response);
		String bookId = js.getString("ID");
		System.out.println("Book id: " + bookId);

		System.out.println("");
		System.out.println("Delete book response-headers and body :");
		System.out.println("---------XXXXXXXXXX-----------");
		given().relaxedHTTPSValidation().body(Payload_Data.deleteBook(bookId)).when().post("/Library/DeleteBook.php")
				.then().assertThat().statusCode(200).log().all();

	}

}
