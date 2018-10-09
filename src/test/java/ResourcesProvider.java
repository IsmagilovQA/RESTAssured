

public class ResourcesProvider {

    public static String resourcePathforGetPlace() {
        String res = "/maps/api/place/nearbysearch/json";
        return res;
    }

    public static String resourcePathforAddPlaceJSON() {
        String res = "/maps/api/place/add/json";
        return res;
    }

    public static String resourcePathforDeletePlace() {
        String res = "/maps/api/place/delete/json";
        return res;
    }

    public static String resourcePathforAddPlaceXML() {
        String res = "/maps/api/place/add/xml";
        return res;
    }
}
