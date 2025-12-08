package org.example;

import cucumber.api.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.*;

import static io.restassured.RestAssured.*;

public class Booking {
    public static Response response;
    public static String path;
    static String baseUrl = "https://restful-booker.herokuapp.com/";
    static String bodyAuth = "{\n" +
            "    \"username\" : \"admin\",\n" +
            "    \"password\" : \"password123\"\n" +
            "}";
    static String bodyBooking = "{\"firstname\" : \"Jim\",\n" +
            "    \"lastname\" : \"Brown\",\n" +
            "    \"totalprice\" : 111,\n" +
            "    \"depositpaid\" : true,\n" +
            "    \"bookingdates\" : {\n" +
            "        \"checkin\" : \"2018-01-01\",\n" +
            "        \"checkout\" : \"2019-01-01\"\n" +
            "    },\n" +
            "    \"additionalneeds\" : \"Breakfast\"}";

    static String wrongBodyBooking = "{\"firstname\" : \"Jim\",\n" +
            "    \"lastname\" : \"Brown\",\n" +
            "    \"totalprice\" : 111,\n" +
            "    \"depositpaid\" : true,\n" +
            "    \"wefwfe\" : {\n" +
            "        \"checkin\" : \"wefe5uj}";
    static String checkoutDate = "\"2056-01-01\"";
    static String bodyUpdate = "{\n" +
            "    \"firstname\" : \"James\",\n" +
            "    \"lastname\" : \"Brown\",\n" +
            "    \"totalprice\" : 111,\n" +
            "    \"depositpaid\" : true,\n" +
            "    \"bookingdates\" : {\n" +
            "        \"checkin\" : \"2018-01-01\",\n" +
            "        \"checkout\" : " + checkoutDate + "\n" +
            "    },\n" +
            "    \"additionalneeds\" : \"Breakfast\"\n" +
            "}";

    @When("^создание токена$")
    public void createToken() {
        String token = BookingRequest.createToken(bodyAuth).jsonPath().getString("token");
        System.out.println("Получили токен: " + token);
    }

    @When("^создание бронирования$")
    public void createBooking() {
        String idBooking = BookingRequest.createBooking(bodyBooking).jsonPath().getString("bookingid");

        Response response = BookingRequest.getBookingID(idBooking);
        Assert.assertEquals(200,response.statusCode());

        System.out.println("Создали и получили id бронирования: " + idBooking);
    }

    @When("^получение бронирвования$")
    public void getBooking() {
        String idBooking = BookingRequest.createBooking(bodyBooking).jsonPath().getString("bookingid");

        Response response = BookingRequest.getBookingID(idBooking);
        Assert.assertEquals(200,response.statusCode());

        System.out.println("Создали и получили id бронирования: " + idBooking);
    }

    @When("^обновление бронирования$")
    public void updateBooking() {

    }

    @When("^частичное обновления бронирования$")
    public void partUpdateBooking() {
        String token = BookingRequest.createToken(bodyAuth).jsonPath().getString("token");
        System.out.println("Получили токен: " + token);

        String idBooking = BookingRequest.createBooking(bodyBooking).jsonPath().getString("bookingid");
        System.out.println("Создали бронирование с id: " + idBooking);

        BookingRequest.updateBooking(bodyUpdate, checkoutDate, idBooking, token);
        Response response = BookingRequest.getBookingID(idBooking);
        Assert.assertEquals(200,response.statusCode());
        System.out.println("Обновили дату бронирования: " + checkoutDate + ". id бронирования: " + idBooking);
    }

    @When("^удаление бронирования$")
    public void удалениеБронирования() {
        String token = BookingRequest.createToken(bodyAuth).jsonPath().getString("token");
        System.out.println("Получили токен: " + token);

        String idBooking = BookingRequest.createBooking(bodyBooking).jsonPath().getString("bookingid");
        System.out.println("Создали бронирование с id: " + idBooking);

        BookingRequest.deleteBooking(bodyAuth, idBooking, token);

        Response response = BookingRequest.getBookingID(idBooking);
        Assert.assertEquals(404,response.statusCode());
        System.out.println("Удалили бронирование с id: " + idBooking);
    }
}
