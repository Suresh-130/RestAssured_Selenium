package org.api.base;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.api.endpoints.BookingEndpoint;
import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class ApiTest extends Base{
    int bookingId;
    String token;

    @Test(priority = 1)
    public void testCreateToken() {
        test = extent.createTest("testCreateToken")
                .assignAuthor("suresh").assignDevice("Windows Desktop");
        Response response = BookingEndpoint.createToken(createToken);
        response.then().log().all();
        token = response.then().statusCode(200)
                .extract()
                .path("token");
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    public void testCreateBooking() throws IOException {
        test = extent.createTest("testCreateBooking")
                .assignAuthor("suresh").assignDevice("Windows Desktop");
        Response response = BookingEndpoint.createBooking(booking);
        bookingId = response.then().log().all().statusCode(200)
                .extract()
                .path("bookingid");
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    public void testGetBookingById() {
        test = extent.createTest("testReadBookingById")
                .assignAuthor("suresh").assignDevice("Windows Desktop");
        Response response = BookingEndpoint.getBookingById(bookingId);
        response.then().log().all();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4)
    public void testUpdateBookingById() {
        test = extent.createTest("testUpdateBookingById")
                .assignAuthor("suresh").assignDevice("Windows Desktop");
        booking.setTotalPrice(100);
        booking.setAdditionalNeeds("BreakFast");
        Response response = BookingEndpoint.updateBookingById(bookingId, booking, token);
        response.then().log().all();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 5)
    public void testDeleteBooking() {
        test = extent.createTest("testDeleteBooking")
                .assignAuthor("suresh").assignDevice("Windows Desktop");
        Response response = BookingEndpoint.deleteBooking(bookingId, token);
        response.then().log().all();
        assertEquals(response.getStatusCode(), 201);
    }


}
