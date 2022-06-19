package com.tinkoff.edu;

import com.tinkoff.edu.decorator.Decorator;
import com.tinkoff.edu.decorator.OrderedDistinctMessageService;
import com.tinkoff.edu.domain.Message;
import com.tinkoff.edu.printer.ConsolePrinter;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.tinkoff.edu.dictionary.SeverityLevel.MAJOR;
import static com.tinkoff.edu.dictionary.SeverityLevel.MINOR;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class ApiIsolatedTest {
    private static Connection connection;
    final String baseUrl = "/api/countries/";
    final String testCountryName = "qa";
    private Integer testCountryId;

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

    @AfterAll
    public static void disconnect() throws SQLException {
        connection.close();
    }

    @BeforeEach
    public void connect() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/app-db",
                "app-db-admin",
                "P@ssw0rd"
        );
    }

    @BeforeEach
    public void createTestCountry() throws SQLException {
        PreparedStatement sql = connection.prepareStatement(
                "INSERT INTO country(country_name) VALUES(?)",
                Statement.RETURN_GENERATED_KEYS
        );
        sql.setString(1, testCountryName);
        sql.executeUpdate();
    }

    @AfterEach
    public void deleteTestCountry() throws SQLException {
        PreparedStatement sqlSearch = connection.prepareStatement(
                "SELECT * from country WHERE country_name = ?"
        );
        sqlSearch.setString(1, testCountryName);
        ResultSet resultSet = sqlSearch.executeQuery();
        if (resultSet.next()) {
            PreparedStatement sqlDelete = connection.prepareStatement(
                    "DELETE FROM country WHERE country_name = ?"
            );
            sqlDelete.setString(1, testCountryName);
            sqlDelete.executeUpdate();
        }
    }

    @Nested
    @DisplayName("Тесты метода GET /api/countries/{id}")
    class getCountryByIdTest {

        @Test
        @DisplayName("Передать существующий в базе id страны")
        public void tryToGetExistingIdTest() throws SQLException {
            List<Integer> countryIds = new ArrayList<>();
            PreparedStatement sqlSearch = connection.prepareStatement(
                    "SELECT * from country WHERE country_name = ?"
            );
            sqlSearch.setString(1, testCountryName);
            ResultSet resultSet = sqlSearch.executeQuery();
            while (resultSet.next()) {
                countryIds.add(resultSet.getInt(1));
            }
            int testCountryId = countryIds.get(0);
            when()
                    .get(""+baseUrl+""+testCountryId+"")
                    .then()
                    .statusCode(200)
                    .body(
                            "id", is(testCountryId),
                            "countryName", is(testCountryName)
                    );
        }

        @Test
        @DisplayName("Передать несуществующий в базе id страны")
        public void tryToGetNotExistingIdTest() throws SQLException{
            Statement sql = connection.createStatement();
            List<Integer> countryIds = new ArrayList<>();
            ResultSet resultSet = sql.executeQuery(
                    "SELECT max(id) FROM country"
            );

            while (resultSet.next()) {
                countryIds.add(resultSet.getInt(1));
            }
            int testCountryId = countryIds.get(0) + 1;
            when()
                    .get(""+baseUrl+""+testCountryId+"")
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

        @Test
        @DisplayName("Передать существующий в базе id и валидное, несуществующее в базе, имя")
        public void putCountryWithExistingIdAndValidNotExistingNameTest() throws SQLException {
            newName = "aq";
            Statement sql = connection.createStatement();
            List<Integer> countryIds = new ArrayList<>();
            ResultSet resultSet = sql.executeQuery(
                    "SELECT max(id) FROM country"
            );

            while (resultSet.next()) {
                countryIds.add(resultSet.getInt(1));
            }
            testCountryId = countryIds.get(0);

            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": "+testCountryId+",\n" +
                            "  \"countryName\": \""+newName+"\"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+testCountryId+"")
                    .then()
                    .statusCode(200)
                    .body("id", is(testCountryId),
                            "countryName", is(newName)
                    );
        }

        @Test
        @DisplayName("Передать несуществующий id")
        public void putCountryWithNotExistingIdTest() throws SQLException {
            newName = "aq";
            Statement sql = connection.createStatement();
            List<Integer> countryIds = new ArrayList<>();
            ResultSet resultSet = sql.executeQuery(
                    "SELECT max(id) FROM country"
            );

            while (resultSet.next()) {
                countryIds.add(resultSet.getInt(1));
            }
            testCountryId = countryIds.get(0) + 1;
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": 1000000,\n" +
                            "  \"countryName\": \""+newName+"\"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+testCountryId+"")
                    .then()
                    .statusCode(400)
                    .body("title", is("Invalid ID")
                    );
        }

        @Test
        @DisplayName("Передать невалидный id")
        public void putCountryWithInvalidIdTest() throws SQLException {
            newName = "aq";
            List<Integer> countryIds = new ArrayList<>();
            PreparedStatement sqlSearch = connection.prepareStatement(
                    "SELECT * from country WHERE country_name = ?"
            );
            sqlSearch.setString(1, testCountryName);
            ResultSet resultSet = sqlSearch.executeQuery();
            while (resultSet.next()) {
                countryIds.add(resultSet.getInt(1));
            }
            testCountryId = countryIds.get(0);
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": \"invalidId\",\n" +
                            "  \"countryName\": \""+newName+"\"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+testCountryId+"")
                    .then()
                    .statusCode(400)
                    .body("title", is("Bad Request")
                    );
        }

        @Test
        @DisplayName("Передать null")
        public void putCountryWithNullIdTest() throws SQLException {
            newName = "aq";
            List<Integer> countryIds = new ArrayList<>();
            PreparedStatement sqlSearch = connection.prepareStatement(
                    "SELECT * from country WHERE country_name = ?"
            );
            sqlSearch.setString(1, testCountryName);
            ResultSet resultSet = sqlSearch.executeQuery();
            while (resultSet.next()) {
                countryIds.add(resultSet.getInt(1));
            }
            testCountryId = countryIds.get(0);
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": null,\n" +
                            "  \"countryName\": \""+newName+"\"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+testCountryId+"")
                    .then()
                    .statusCode(400)
                    .body("title", is("Invalid id")
                    );
        }

        @Test
        @DisplayName("Не передавать id")
        public void putCountryWithoutIdTest() throws SQLException {
            newName = "aq";
            List<Integer> countryIds = new ArrayList<>();
            PreparedStatement sqlSearch = connection.prepareStatement(
                    "SELECT * from country WHERE country_name = ?"
            );
            sqlSearch.setString(1, testCountryName);
            ResultSet resultSet = sqlSearch.executeQuery();
            while (resultSet.next()) {
                countryIds.add(resultSet.getInt(1));
            }
            testCountryId = countryIds.get(0);
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"countryName\": \""+newName+"\"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+testCountryId+"")
                    .then()
                    .statusCode(400)
                    .body("title", is("Invalid id")
                    );
        }

        @Test
        @DisplayName("Передать невалидное имя страны")
        public void putCountryWithInvalidNameTest() throws SQLException {
            newName = "abc";
            List<Integer> countryIds = new ArrayList<>();
            PreparedStatement sqlSearch = connection.prepareStatement(
                    "SELECT * from country WHERE country_name = ?"
            );
            sqlSearch.setString(1, testCountryName);
            ResultSet resultSet = sqlSearch.executeQuery();
            while (resultSet.next()) {
                countryIds.add(resultSet.getInt(1));
            }
            testCountryId = countryIds.get(0);
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": "+testCountryId+",\n" +
                            "  \"countryName\": \""+newName+"\"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+testCountryId+"")
                    .then()
                    .statusCode(400)
                    .body("fieldErrors[0].field", is("countryName"),
                            "fieldErrors[0].message", is("size must be between 2 and 2")
                    );
        }

        @Test
        @DisplayName("Передать null в имени страны")
        public void putCountryWithNullNameTest() throws SQLException {
            List<Integer> countryIds = new ArrayList<>();
            PreparedStatement sqlSearch = connection.prepareStatement(
                    "SELECT * from country WHERE country_name = ?"
            );
            sqlSearch.setString(1, testCountryName);
            ResultSet resultSet = sqlSearch.executeQuery();
            while (resultSet.next()) {
                countryIds.add(resultSet.getInt(1));
            }
            testCountryId = countryIds.get(0);
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": "+testCountryId+",\n" +
                            "  \"countryName\": null\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+testCountryId+"")
                    .then()
                    .statusCode(400)
                    .body("fieldErrors[0].field", is("countryName"),
                            "fieldErrors[0].message", is("must not be null")
                    );
        }

        @Test
        @DisplayName("Не передавать имя страны")
        public void putCountryWithoutNameTest() throws SQLException {
            List<Integer> countryIds = new ArrayList<>();
            PreparedStatement sqlSearch = connection.prepareStatement(
                    "SELECT * from country WHERE country_name = ?"
            );
            sqlSearch.setString(1, testCountryName);
            ResultSet resultSet = sqlSearch.executeQuery();
            while (resultSet.next()) {
                countryIds.add(resultSet.getInt(1));
            }
            testCountryId = countryIds.get(0);
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": "+testCountryId+"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+testCountryId+"")
                    .then()
                    .statusCode(400)
                    .body("fieldErrors[0].field", is("countryName"),
                            "fieldErrors[0].message", is("must not be null")
                    );
        }

        @Test
        @DisplayName("Передать существующее в базе имя страны")
        public void putCountryWithExistingNameTest() throws SQLException {
            newName = "aq";
            List<Integer> countryIds = new ArrayList<>();
            PreparedStatement sql = connection.prepareStatement(
                    "INSERT INTO country(country_name) VALUES(?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            sql.setString(1, newName);
            sql.executeUpdate();
            ResultSet resultSet = sql.getGeneratedKeys();
            while (resultSet.next()) {
                countryIds.add(resultSet.getInt(1));
            }
            int newTestCountryId = countryIds.get(0);
            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"id\": "+newTestCountryId+",\n" +
                            "  \"countryName\": \""+testCountryName+"\"\n" +
                            "}")
                    .when()
                    .put(""+baseUrl+""+newTestCountryId+"")
                    .then()
                    .statusCode(500)
                    .body("title", is("Internal Server Error")
                    );
            PreparedStatement sqlDelete = connection.prepareStatement(
                    "DELETE FROM country WHERE country_name = ?"
            );
            sqlDelete.setString(1, newName);
            sqlDelete.executeUpdate();
        }
    }

    @Nested
    @DisplayName("Тесты метода DELETE /api/countries/{id}")
    class deleteCountryByIdTest {

        @Test
        @DisplayName("Передать существующий id")
        public void deleteExistingIdTest() throws SQLException {
            List<Integer> countryIds = new ArrayList<>();
            PreparedStatement sqlSearch = connection.prepareStatement(
                    "SELECT * from country WHERE country_name = ?"
            );
            sqlSearch.setString(1, testCountryName);
            ResultSet resultSet = sqlSearch.executeQuery();
            while (resultSet.next()) {
                countryIds.add(resultSet.getInt(1));
            }
            testCountryId = countryIds.get(0);
            when()
                    .delete(""+baseUrl+""+ testCountryId +"")
                    .then()
                    .statusCode(204);
        }

        @Test
        @DisplayName("Передать несуществующий id")
        public void deleteNotExistingIdTest() throws SQLException {
            Statement sql = connection.createStatement();
            List<Integer> countryIds = new ArrayList<>();
            ResultSet resultSet = sql.executeQuery(
                    "SELECT max(id) FROM country"
            );

            while (resultSet.next()) {
                countryIds.add(resultSet.getInt(1));
            }
            testCountryId = countryIds.get(0) + 1;
            when()
                    .delete(""+baseUrl+""+testCountryId+"")
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

        @Test
        @DisplayName("Создать страну с валидным, несуществующим в базе названием")
        public void createCountryWithNotExistingName() throws SQLException {
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

            PreparedStatement sqlDelete = connection.prepareStatement(
                    "DELETE FROM country WHERE country_name = ?"
            );
            sqlDelete.setString(1, newName);
            sqlDelete.executeUpdate();
        }

        @Test
        @DisplayName("Создать страну с валидным, существующим в базе названием")
        public void createCountryWithExistingName() {

            given()
                    .contentType("application/json")
                    .body("{\n" +
                            "  \"countryName\": \"" + testCountryName + "\"\n" +
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
