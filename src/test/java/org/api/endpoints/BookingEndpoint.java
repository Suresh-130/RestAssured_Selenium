package org.api.endpoints;

import static io.restassured.RestAssured.given;

import org.api.restfullbooker.Booking;
import org.api.restfullbooker.CreateToken;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BookingEndpoint {
    public static Response createToken(CreateToken createToken) {

        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(createToken)
                .when()
                .post(Routes.auth_post_url);
    }

    public static Response createBooking(Booking booking) {
        return given()
                //.log().all()
                .contentType(ContentType.JSON)
                .body(booking)
                .when()
                .post(Routes.booking_post_url);
    }
    public static Response getBookingById(int bookingid) {
        return given()
                .pathParam("id", bookingid)
                .when()
                .get(Routes.booking_get_url);
    }
    public static Response updateBookingById(int bookingid,Booking booking,String token) {
        return given()
                .header("Cookie", "token=" + token)
               // .log().all()
                .contentType(ContentType.JSON)
                .pathParam("id", bookingid)
                .body(booking)
                .when()
                .put(Routes.booking_update_url);
    }

    public static Response deleteBooking(int bookingid,String token) {
        return given()
                .header("Cookie", "token=" + token)
                .pathParam("id", bookingid)
                .when()
                .delete(Routes.booking_delete_url);
    }

}
