
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class API_Tests {

    Properties prop = new Properties();

    @BeforeTest
    public void getData() throws IOException {
        FileInputStream fis = new FileInputStream("/Users/ismagilov/IdeaProjects/RESTAssured/src/test/resources/environment.properties");
        prop.load(fis);
        prop.get("HOST");
    }


    @Test
    public void test_GET_search_place() {
        RestAssured.baseURI = prop.getProperty("HOST"); // Take HOST from 'environment.properties' file
        Response res = given().log().ifValidationFails()
                .param("location", "-33.8670522,151.1957362")
                .param("radius", 500)
                .param("key", prop.getProperty("KEY"))

                .when()
                .get(ResourcesProvider.resourcePathforGetPlace())

                .then().assertThat()
                .statusCode(200).and()
                .contentType(ContentType.JSON).and()
                .body("results[0].name", equalTo("Sydney")).and()
                .body("results[0].place_id", equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).and()
                .header("Server", "scaffolding on HTTPServer2")
                .log().ifValidationFails()

                // Store and display all 20 'name' from response (from Results: [20] - array)
                .extract().response();
        JsonPath js = ReusableMethods.rawToJSON(res); // convert raw data to JSON

        // find out what's the size of array
        int count = js.get("results.size()");
        System.out.println(count + " - quantity of results");

        // get from response (json) all 'name' objects
        for (int i = 0; i < count; i++) {
            String name = js.get("results[" + i + "].name");
            System.out.println(name);
        }

        // get from response collections with all names (almost the same as we did above with for loop)
        List <String> all_names = js.get("results.name");
        System.out.println(all_names + " All names");
    }


    @Test
    public void test_POST_add_place_JSON() {
        RestAssured.baseURI = prop.getProperty("HOST"); // Take HOST from 'environment.properties' file
        Response resp = given()
                .queryParam("key", prop.getProperty("KEY"))
                .body(BodyDataProvider.getBodyFromAddPlaceJSON())

                .when()
                .post(ResourcesProvider.resourcePathforAddPlaceJSON())

                .then().assertThat()
                .statusCode(200).and()
                .contentType(ContentType.JSON).and()
                .body("status", equalTo("OK"))

                .extract().response();

        JsonPath x = ReusableMethods.rawToJSON(resp);
        String id = x.get("id");
        System.out.println(id + " it's 'id' from response");


    }


    @Test
    public void test_POST_add_place_XML() throws IOException {
        RestAssured.baseURI = prop.getProperty("HOST"); // Take HOST from 'environment.properties' file
        Response resp = given()
                .queryParam("key", prop.getProperty("KEY"))
                .body(BodyDataProvider.generateStringFromResourceXML("/Users/ismagilov/IdeaProjects/RESTAssured/src/test/resources/bodyPostRequestXML.xml"))

                .when()
                .post(ResourcesProvider.resourcePathforAddPlaceXML())

                .then().assertThat()
                .statusCode(200).and()
                .contentType(ContentType.XML)
                .body("PlaceAddResponse.status", equalTo("OK"))

                .extract().response();

        XmlPath x = ReusableMethods.rawToXML(resp);
        String placeID = x.get("PlaceAddResponse.place_id");
        System.out.println(placeID + " it's place ID from response");

    }


    @Test
    public void test_add_and_delete_place() {

        // Step 1 - Add the Place and Extract the response body
        RestAssured.baseURI = prop.getProperty("HOST"); // Take HOST from 'environment.properties' file
        Response res = given()
                .queryParam("key", prop.getProperty("KEY"))
                .body(BodyDataProvider.getBodyFromAddPlaceJSON())

                .when()
                .post(ResourcesProvider.resourcePathforAddPlaceJSON())

                .then().assertThat()
                .statusCode(200).and()
                .contentType(ContentType.JSON).and()
                .body("status", equalTo("OK")).and()

                .extract().response();
        // String responseAsString = res.asString(); // for displaying whole response in log (two lines)
        // System.out.println(responseAsString);

        // Step 2 - Take 'Place_id' from response body
        JsonPath x = ReusableMethods.rawToJSON(res);
        String placeID = x.get("place_id");
        System.out.println(placeID + " it's place_id from response");

        // Step 3 - Use this placeID in Delete request
        given()
                .queryParam("key", prop.getProperty("KEY"))
                .body("{\n" +
                        "  \"place_id\": \"" + placeID + "\"\n" +
                        "}")

                .when()
                .post(ResourcesProvider.resourcePathforDeletePlace())

                .then().assertThat()
                .statusCode(200).and()
                .contentType(ContentType.JSON).and()
                .body("status", equalTo("OK"));
    }
}
