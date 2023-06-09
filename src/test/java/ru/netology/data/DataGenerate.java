package ru.netology.data;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;


import java.util.Locale;

import static io.restassured.RestAssured.given;
public class DataGenerate {

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker faker = new Faker(new Locale("en"));
private DataGenerate(){
}
    private static void sendRequest(RegistrationUserData user) {
        //Запрос
        given() //"дано"
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getLogin() {
    String login = faker.name().username();
        return login;
    }

    public static String getPassword() {
      String password = faker.internet().password();
      return password;
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationUserData getUser(String status) {
            return new RegistrationUserData(getLogin(), getPassword(), status);
        }

        public static RegistrationUserData registerUser(String status) {
            var registerUser = getUser(status);
            sendRequest(registerUser);
            return registerUser;
        }
    }
  @Value
    public static class RegistrationUserData {
        String login;
        String password;
        String status;
    }

}