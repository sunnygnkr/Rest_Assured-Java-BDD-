package Crud_Operations;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import org.testng.annotations.Test;

public class GetRequest {

    @Test
    public static void getResponse() {
       RestAssured.baseURI = "https://rahulshettyacademy.com";

         given().relaxedHTTPSValidation().log().all().
                 queryParam("key","qaclick123").
                when().get("/maps/api/place/get/json").
                then().log().all();

    }

}
