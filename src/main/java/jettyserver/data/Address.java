package jettyserver.data;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Address {
    private final static String ApiKey = "&key=AIzaSyDi6VnksOVdCeYw5g8R1V-uVT9O7VgGBic";
    private final static String UrlBase =  "https://maps.googleapis.com/maps/api/";
    private final static String AddressQuery = UrlBase + "geocode/json?address=%s" + ApiKey;
    private final static String DistanceQuery = UrlBase + "distancematrix/json?origins=%s&destinations=%s" + ApiKey;
    // location: https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyDi6VnksOVdCeYw5g8R1V-uVT9O7VgGBic
    // distance: https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver+BC|Seattle&destinations=San+Francisco|Victoria+BC&key=YOUR_API_KEY

    public static LocationRecord getLocation(String address) throws IOException, ParseException {
        String query = String.format(AddressQuery,  URLEncoder.encode(address, "UTF-8"));
        URL url = new URL(query);
        String queryResult = IOUtils.toString(url, "UTF-8");
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(queryResult);
        JSONObject result = (JSONObject) ((JSONArray) json.get("results")).get(0);
        JSONObject geometry = (JSONObject) result.get("geometry");
        JSONObject location = (JSONObject) geometry.get("location");
        return new LocationRecord(address, (Double)location.get("lat"), (Double)location.get("lng"));
    }
}
