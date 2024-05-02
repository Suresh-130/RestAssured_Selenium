package org.api.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.api.restfullbooker.Booking;
import org.api.restfullbooker.BookingDates;
import org.api.restfullbooker.CreateToken;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

public class Base {
	private static final Logger logger = LogManager.getLogger(Base.class.getName());
	public static ExtentReports extent;
	public CreateToken createToken;
	public Booking booking;
	public Faker faker;
	public static ExtentTest test;
	ObjectMapper objectMapper = new ObjectMapper();
	String json;

	@BeforeTest
	public void setUp() {
		logger.info("Report Setup in each Test");
		ExtentSparkReporter spark = new ExtentSparkReporter("./reports/Extentreport.html");
		extent = new ExtentReports();
		extent.attachReporter(spark);
		spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle("MyReport");
		spark.config().setReportName("Test Report");
		spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
	}

	@BeforeClass
	public void setupData() throws JsonProcessingException {
		faker = new Faker();
		createToken = new CreateToken();
		booking = new Booking();
		booking.setFirstname(faker.name().username());
		booking.setLastname(faker.name().lastName());
		booking.setAdditionalNeeds(faker.random().hex());
		booking.setBookingDates(new BookingDates(faker.date().past(5, java.util.concurrent.TimeUnit.DAYS).toString(),  faker.date().future(2, java.util.concurrent.TimeUnit.DAYS).toString()));
		booking.setTotalPrice(faker.number().randomDigit());
		booking.setDepositPaid(faker.bool().bool());
		createToken.setUsername("admin");
		createToken.setPassword("password123");
		logger.info(createToken);
		logger.info(booking);
	}

	@AfterMethod
	public void getResult(ITestResult result) {
		logger.info("Report Result in each Test");
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, result.getThrowable());
			test.log(Status.FAIL, "Test Case Failed");
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Test Case Passed Sucessfully");
		} else {
			test.log(Status.SKIP, result.getTestName());
		}
	}

	@AfterTest
	public void endReport() {
		extent.flush();
	}

}
