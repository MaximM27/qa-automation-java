package com.tinkoff.edu;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CountryResourceTest {
    private Connection connection;

    final String baseUrl = "/api/countries/";

    @BeforeAll
    public static void setUpAuth() {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("admin");
        authScheme.setPassword("admin");
        RestAssured.authentication = authScheme;
    }

    @BeforeAll
    public static void setUpErrorLogging() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    @DisplayName("Тесты метода GET /api/countries/{id}")
    class getCountryByIdTest {

        private Integer countryId;
        private String countryName;

        @Test
        @DisplayName("Передать существующий в базе id страны")
        public void tryToGetExistingIdTest() {
            countryId = Integer.valueOf(get("" + baseUrl + "").then().extract().response().path("id[0]").toString());
            countryName = get("" + baseUrl + "").then().extract().response().path("countryName[0]").toString();
            when()
                    .get(""+baseUrl+""+countryId+"")
            .then()
                    .statusCode(200)
                    .body(
                            "id", is(countryId),
                            "countryName", is(countryName)
                    );
        }

        @Test
        @DisplayName("Передать несуществующий в базе id страны")
        public void tryToGetNotExistingIdTest() {
            when()
                    .get(""+baseUrl+"1000000")
            .then()
                    .statusCode(404)
                    .body(
                            "title", is("Not Found")
                    );
        }

        @Test
        @DisplayName("Передать невалидный id")
        public void tryToGetInvalidIdTest() {
            when()
                    .get(""+baseUrl+"id")
            .then()
                    .statusCode(400)
                    .body("title", is("Bad Request")
                    );
        }
    }

    @Nested
    @DisplayName("Тесты метода PUT /api/countries/{id}}")
    class putCountryByIdTest {

        private String newName;
        private Integer nextCountryId;
        private Integer countryId;

        @Test
        @DisplayName("Передать существующий в базе id и валидное имя, несуществующее в базе имя")
        public void putCountryWithExistingIdAndValidNotExistingNameTest() {
            newName = "aq";
            var response = given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"countryName\": \"" + newName + "\"\n" +
                            "}")
                    .when()
                    .post("" + baseUrl + "")
                    .then()
                    .statusCode(201)
                    .body("id", not(empty()),
                            "countryName", is(newName));

            countryId = response.extract().response().path("id");

            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": "+countryId+",\n" +
                            "  \"countryName\": \""+newName+"\"\n" +
                            "}")
            .when()
                    .put(""+baseUrl+""+countryId+"")
            .then()
                    .statusCode(200)
                    .body("id", is(countryId),
                            "countryName", is(newName)
                    );
        }

        @Test
        @DisplayName("Передать несуществующий id")
        public void putCountryWithNotExistingIdTest() {
            newName = "aq";
            countryId = Integer.valueOf(get("" + baseUrl + "").then().extract().response().path("id[0]").toString());
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": 1000000,\n" +
                            "  \"countryName\": \""+newName+"\"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+countryId+"")
                    .then()
                    .statusCode(400)
                    .body("title", is("Invalid ID")
                    );
        }

        @Test
        @DisplayName("Передать невалидный id")
        public void putCountryWithInvalidIdTest() {
            newName = "aq";
            countryId = Integer.valueOf(get("" + baseUrl + "").then().extract().response().path("id[0]").toString());
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": \"invalidId\",\n" +
                            "  \"countryName\": \""+newName+"\"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+countryId+"")
                    .then()
                    .statusCode(400)
                    .body("title", is("Bad Request")
                    );
        }

        @Test
        @DisplayName("Передать невалидный id")
        public void putCountryWithNullIdTest() {
            newName = "aq";
            countryId = Integer.valueOf(get("" + baseUrl + "").then().extract().response().path("id[0]").toString());
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": null,\n" +
                            "  \"countryName\": \""+newName+"\"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+countryId+"")
                    .then()
                    .statusCode(400)
                    .body("title", is("Invalid id")
                    );
        }

        @Test
        @DisplayName("Не передавать id")
        public void putCountryWithoutIdTest() {
            newName = "aq";
            countryId = Integer.valueOf(get("" + baseUrl + "").then().extract().response().path("id[0]").toString());
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"countryName\": \""+newName+"\"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+countryId+"")
                    .then()
                    .statusCode(400)
                    .body("title", is("Invalid id")
                    );
        }

        @Test
        @DisplayName("Передать в payload id, несовпадающий с id в url запроса")
        public void putCountryWithNotMatchedIdTest() {
            newName = "aq";
            countryId = Integer.valueOf(get("" + baseUrl + "").then().extract().response().path("id[0]").toString());
            nextCountryId = Integer.valueOf(get("" + baseUrl + "").then().extract().response().path("id[1]").toString());
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": "+nextCountryId+",\n" +
                            "  \"countryName\": \""+newName+"\"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+countryId+"")
                    .then()
                    .statusCode(400)
                    .body("title", is("Invalid ID")
                    );
        }

        @Test
        @DisplayName("Передать невалидное имя страны")
        public void putCountryWithInvalidNameTest() {
            newName = "abc";
            countryId = Integer.valueOf(get("" + baseUrl + "").then().extract().response().path("id[0]").toString());
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": "+countryId+",\n" +
                            "  \"countryName\": \""+newName+"\"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+countryId+"")
                    .then()
                    .statusCode(400)
                    .body("fieldErrors[0].field", is("countryName"),
                            "fieldErrors[0].message", is("size must be between 2 and 2")
                    );
        }

        @Test
        @DisplayName("Передать null в имени страны")
        public void putCountryWithNullNameTest() {
            countryId = Integer.valueOf(get("" + baseUrl + "").then().extract().response().path("id[0]").toString());
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": "+countryId+",\n" +
                            "  \"countryName\": null\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+countryId+"")
                    .then()
                    .statusCode(400)
                    .body("fieldErrors[0].field", is("countryName"),
                            "fieldErrors[0].message", is("must not be null")
                    );
        }

        @Test
        @DisplayName("Не передавать имя страны")
        public void putCountryWithoutNameTest() {
            countryId = Integer.valueOf(get("" + baseUrl + "").then().extract().response().path("id[0]").toString());
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": "+countryId+"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+countryId+"")
                    .then()
                    .statusCode(400)
                    .body("fieldErrors[0].field", is("countryName"),
                            "fieldErrors[0].message", is("must not be null")
                    );
        }

        @Test
        @DisplayName("Передать существующее в базе имя страны")
        public void putCountryWithExistingNameTest() {
            String existingName = get(""+baseUrl+"").then().extract().response().path("countryName[0]").toString();
            nextCountryId = Integer.valueOf(get("" + baseUrl + "").then().extract().response().path("id[1]").toString());
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": "+nextCountryId+",\n" +
                            "  \"countryName\": \""+existingName+"\"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+nextCountryId+"")
                    .then()
                    .statusCode(500)
                    .body("title", is("Internal Server Error")
                    );
        }
    }

    @Nested
    @DisplayName("Тесты метода DELETE /api/countries/{id}")
    class deleteCountryByIdTest {

        @Test
        @DisplayName("Передать существующий id")
        public void deleteExistingIdTest() {
            int deletedId = Integer.parseInt(get("" + baseUrl + "").then().extract().response().path("id[0]").toString());
            when()
                    .delete(""+baseUrl+""+ deletedId +"")
            .then()
                    .statusCode(204);
        }

        @Test
        @DisplayName("Передать несуществующий id")
        public void deleteNotExistingIdTest() {
            when()
                    .delete(""+baseUrl+"1000000")
            .then()
                    .statusCode(500)
                    .body("title", is("Internal Server Error")
                    );
        }

        @Test
        @DisplayName("Передать невалидный id")
        public void deleteInvalidIdTest() {
            when()
                    .delete(""+baseUrl+"id")
            .then()
                    .statusCode(400)
                    .body("title", is("Bad Request")
                    );
        }
    }

    @Nested
    @DisplayName("Тесты метода POST /api/countries/{id}")
    class createNewCountryTest {

        private String newName;
        private Integer countryId = null;

        @AfterEach
        public void cleanTestData() {
            if (countryId != null) {
                when()
                    .delete(""+baseUrl+""+countryId+"")
                    .then()
                    .statusCode(204);
            }
        }

        @Test
        @DisplayName("Создать страну с валидным, несуществующим в базе названием")
        public void createCountryWithNotExistingName() {
            newName = "aq";

            var response = given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"countryName\": \"" + newName + "\"\n" +
                            "}")
                    .when()
                    .post("" + baseUrl + "")
                    .then()
                    .statusCode(201)
                    .body("id", not(empty()),
                            "countryName", is(newName));

            countryId = response.extract().response().path("id");
        }

        @Test
        @DisplayName("Создать страну с валидным, существующим в базе названием")
        public void createCountryWithExistingName() {
            newName = get("" + baseUrl + "").then().extract().response().path("countryName[0]").toString();

            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"countryName\": \"" + newName + "\"\n" +
                            "}")
                    .when()
                    .post("" + baseUrl + "")
                    .then()
                    .statusCode(500)
                    .body("title", is("Internal Server Error"));

        }

        @Test
        @DisplayName("Создать страну с невалидным названием")
        public void createCountryWithInvalidName() {
            newName = "abc";

            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"countryName\": \"" + newName + "\"\n" +
                            "}")
                    .when()
                    .post("" + baseUrl + "")
                    .then()
                    .statusCode(400)
                    .body("fieldErrors[0].field", is("countryName"),
                            "fieldErrors[0].message", is("size must be between 2 and 2")
                    );
        }
    }
}
