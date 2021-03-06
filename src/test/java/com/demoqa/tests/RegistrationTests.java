package com.demoqa.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.demoqa.Attach;
import com.demoqa.config.CredentialsConfig;
import com.demoqa.pages.RegistrationFormPage;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

@Owner("Burmis")
@Feature("Registration")
@DisplayName("Registration tests")
public class RegistrationTests {
    static CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
    static String login = config.login();
    static String password = config.password();
    RegistrationFormPage registrationFormPage = new RegistrationFormPage();
    Faker faker = new Faker();
    String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            email = faker.internet().emailAddress(),
            gender = "Male",
            phoneNumber = faker.numerify("##########"),
            day = "29",
            month = "October",
            year = "1990",
            subject = "English",
            hobby1 = "Sports",
            hobby2 = "Reading",
            hobby3 = "Music",
            imgPath = "photo2.jpg",
            address = faker.address().fullAddress(),
            state = "NCR",
            city = "Delhi";


    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.browser = System.getProperty("browser");
        Configuration.baseUrl = System.getProperty("base_url");
        Configuration.browserSize = System.getProperty("browser_size");
        Configuration.remote = "https://" + login + ":" + password + "@" + System.getProperty("server_selenoid");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }

    @Test
    @DisplayName("Successful registration")
    void successfulRegistration() {
        Map<String, String> map = new HashMap<>();
        map.put("Student Name", (firstName + " " + lastName));
        map.put("Student Email", email);
        map.put("Gender", gender);
        map.put("Mobile", phoneNumber);
        map.put("Date of Birth", (day + " " + month + "," + year));
        map.put("Subjects", subject);
        map.put("Hobbies", hobby1);
        map.put("Hobbies", hobby2);
        map.put("Hobbies", hobby3);
        map.put("Picture", imgPath);
        map.put("Address", address);
        map.put("State and City", (state + " " + city));
        registrationFormPage.openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setGender(gender)
                .setPhoneNumber(phoneNumber)
                .setBirthDate(day, month, year)
                .setSubject(subject)
                .setHobbies(hobby1, hobby2, hobby3)
                .setPicture(imgPath)
                .setAddress(address)
                .setState(state)
                .setCity(city)
                .submit()
                .checkResult(new HashMap<>());
    }

    @ValueSource(strings = {
            "1900",
            "2000"
    })

    @ParameterizedTest(name = "with {0} year")
    @DisplayName("Successful registration")
    void successfulParametrizedRegistration(String testData) {
        registrationFormPage.openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setGender(gender)
                .setPhoneNumber(phoneNumber)
                .setBirthDate(day, month, testData)
                .setSubject(subject)
                .setHobbies(hobby1, hobby2, hobby3)
                .setPicture(imgPath)
                .setAddress(address)
                .setState(state)
                .setCity(city)
                .submit()
                .checkResult(new HashMap<>());
    }
}








