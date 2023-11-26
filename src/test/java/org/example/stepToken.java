package org.example;

import cucumber.api.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.*;
import io.restassured.response.Response;
import io.restassured.specification.*;
import org.junit.runner.*;
import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.Assertions;
import static io.restassured.RestAssured.given;

public class stepToken {
    private Response response;
    private String path;

    static String baseUrl = "https://restful-booker.herokuapp.com/";
    static String bodyAuth = "{\n" +
            "    \"username\" : \"admin\",\n" +
            "    \"password\" : \"password123\"\n" +
            "}";
    @Given("^дан URL$")
    public void haveURLSendBody() {
        RestAssured.baseURI = baseUrl;
        path = "/auth";
    }

    @When("^пользователь отправляет тело запроса$")
    public void userSendRequest() {
        response = given()
                .contentType(ContentType.JSON)
                .body(bodyAuth)
                .post(path)
                .then().extract().response();
    }

    @Then("^пользователь получает токен$")
    public void userGotToken() {
        String token = response.jsonPath().getString("token");
        System.out.println("Пользователь получил токен: " + token);
    }
}
