import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;


public class ReusableMethods {

    // method which convert raw data to XML
    public static XmlPath rawToXML(Response r) {
        String responseAsString = r.asString();
        // System.out.println(responseAsString);
        XmlPath x = new XmlPath(responseAsString);
        return x;
    }

    // method which convert raw data to JSON
    public static JsonPath rawToJSON(Response r) {
        String responseAsString = r.asString();
        // System.out.println(responseAsString);
        JsonPath x = new JsonPath(responseAsString);
        return x;
    }
}
