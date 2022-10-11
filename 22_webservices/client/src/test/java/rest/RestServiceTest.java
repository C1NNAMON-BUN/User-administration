package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class RestServiceTest {
    static String token;
    static final String BASE_URI = "http://zhilina-webservices:8080/";

    @BeforeClass
    public static void getToken() {
        RestAssured.baseURI = BASE_URI;
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Content-Type", "application/json");
        Response responseFromGenerateToken = requestSpecification.body(readFileAsString("AdminObjectForAuth.json")).post("/login");
        String jsonString = responseFromGenerateToken.getBody().asString();

        token = "Bearer_" + JsonPath.from(jsonString).get("token");
    }

    @SneakyThrows
    @Test
    public void testGetUserWithId() {
        Assert.assertEquals(given()
                .baseUri(BASE_URI)
                .basePath("/user").param("id", "1")
                .contentType(ContentType.JSON)
                .header("Token", token)
                .when()
                .get().then()
                .statusCode(200)
                .extract().asString(), readFileAsString("GetOneUserWithId.json"));
    }


    @SneakyThrows
    @Test
    public void btestzGetAllUsers() {
       given()
                .baseUri(BASE_URI)
                .basePath("/users")
                .contentType(ContentType.JSON)
                .header("Token", token)
                .when()
                .get().then()
                .statusCode(200);
    }

    @SneakyThrows
    @Test
    public void testAddUser() {
        String json = given()
                .baseUri(BASE_URI)
                .basePath("/user")
                .contentType(ContentType.JSON)
                .header("Token", token)
                .body(readFileInputStreamAsString("AddNewUser.json"))
                .when()
                .post().then().statusCode(200)
                .extract().asString();

        JSONObject writtenObject = new JSONObject(json);

        Assert.assertNotNull(writtenObject.get("id"));
        Assert.assertNotNull(writtenObject.get("login"));
        Assert.assertNotNull(writtenObject.get("password"));
        Assert.assertNotNull(writtenObject.get("email"));
        Assert.assertNotNull(writtenObject.get("firstName"));
        Assert.assertNotNull(writtenObject.get("lastName"));
        Assert.assertNotNull(writtenObject.get("birthday"));
        Assert.assertNotNull(writtenObject.get("roleEntity"));
    }

    @SneakyThrows
    @Test
    @SuppressWarnings("unchecked")
    public void testUpdateUser() {
        String json = given()
                .baseUri(BASE_URI)
                .basePath("/user")
                .contentType(ContentType.JSON)
                .header("Token", token)
                .body(readFileInputStreamAsString("UpdateNewUser.json"))
                .when()
                .put().then()
                .statusCode(200)
                .extract().asString();

        JSONObject writtenObject = new JSONObject(json);
        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String, String> object = (LinkedHashMap<String, String>) mapper.readValue(readFileAsString("UpdateNewUser.json"), Object.class);

        Assert.assertEquals(writtenObject.get("id"), object.get("id"));
        Assert.assertEquals(writtenObject.get("login"), object.get("login"));
        Assert.assertNotNull(object.get("password"));
        Assert.assertEquals(writtenObject.get("email"), object.get("email"));
        Assert.assertEquals(writtenObject.get("firstName"), object.get("firstName"));
        Assert.assertEquals(writtenObject.get("lastName"), object.get("lastName"));
        Assert.assertEquals(writtenObject.get("birthday"), object.get("birthday"));
    }

    @SneakyThrows
    @Test
    public void testRemoveUser() {
        given()
                .baseUri(BASE_URI)
                .basePath("/user")
                .contentType(ContentType.JSON)
                .header("Token", token)
                .param("id", "5")
                .when()
                .delete().then().statusCode(200);
    }

    @SneakyThrows
    public static String readFileAsString(String file) {
        return new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(RestServiceTest.class.getClassLoader()
                        .getResource(file))
                .getFile())));
    }

    public static InputStream readFileInputStreamAsString(String file) {
        return RestServiceTest.class.getClassLoader().getResourceAsStream(file);
    }
}