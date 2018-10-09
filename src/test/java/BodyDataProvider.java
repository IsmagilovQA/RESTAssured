import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class BodyDataProvider {

    public static String getBodyFromAddPlaceJSON() {
        String bodyOfRequestJSON = "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -33.8669710,\n" +
                "    \"lng\": 151.1958750\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"Google Shoes!\",\n" +
                "  \"phone_number\": \"(02) 9374 4000\",\n" +
                "  \"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\",\n" +
                "  \"types\": [\"shoe_store\"],\n" +
                "  \"website\": \"http://www.google.com.au/\",\n" +
                "  \"language\": \"en-AU\"\n" +
                "}";
        return bodyOfRequestJSON;
    }

    // method for converting XML to String
    public static String generateStringFromResourceXML(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
