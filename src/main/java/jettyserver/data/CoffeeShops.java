package jettyserver.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;
/**
 * Created by difu on 06/27/2017.
 */
public class CoffeeShops {
    private Map<Integer, LocationRecord> locations;
    private Map<String, Integer> index;
    private int nextId;

    private static CoffeeShops shops = null;

    public Map<Integer, LocationRecord> getLocations() {
        return locations;
    }

    private CoffeeShops() {
        locations = new HashMap<Integer, LocationRecord>();
        index = new HashMap<String, Integer>();
    }

    public LocationRecord getById(int id) {
        return this.locations.get(id);
    }

    public LocationRecord getByName(String name) {
        Integer id = index.get(name);
        if (id == null)
            return null;
        return this.locations.get(id);
    }

    public LocationRecord findNear(LocationRecord target) {
        LocationRecord result = null;
        double distance = Double.MAX_VALUE;;

        for (LocationRecord record: locations.values()) {
            double dist = target.distance(record);
            if (distance > dist ) {
                result = record;
                distance = dist;
            }
        }
        return result;
    }

    public synchronized Integer add(String name, String address, String lat, String lng) {
        if (index.get(name) != null) // existing, cannot reate
            return null;
        Integer id = nextId++;
        LocationRecord record = new LocationRecord(
                id, name, address, Double.parseDouble(lat), Double.parseDouble(lng)
        );
        index.put(name, id);
        locations.put(id, record);
        return id;
    }

    public synchronized LocationRecord update(String idString, String name, String address, String lat, String lng) {
        Integer id = Integer.parseInt(idString);
        LocationRecord record = this.getById(id);
        if (record == null)
            return null;
        if (name != null && ! name.isEmpty())
            record.name = name;
        if (address != null && ! address.isEmpty())
            record.address = address;
        if (lat != null) {
            record.lat = Double.parseDouble(lat);
        }
        if (lng != null) {
            record.longt = Double.parseDouble(lng);
        }
        locations.put(id, record);
        return record;
    }

    public synchronized boolean delete(Integer id) {
        LocationRecord record = locations.get(id);
        if (record == null)
            return false;
        index.remove(record.name);
        locations.remove(id);
        return true;
    }

    public static CoffeeShops get()  throws IOException {
        if (shops != null)
            return shops;
        shops = new CoffeeShops();
        ClassLoader loader = CoffeeShops.class.getClassLoader();
        int maxId = 0;
        for (Enumeration<URL> urls = loader.getResources("locations.csv"); urls.hasMoreElements();) {
            URL url = urls.nextElement();
            if (url != null) {
                CSVParser parser = CSVParser.parse(
                        url,
                        Charset.defaultCharset(),
                        CSVFormat.DEFAULT
                );
                for (CSVRecord csvRecord : parser) {
                    LocationRecord record = new LocationRecord(csvRecord);
                    shops.locations.put(record.id, record);
                    shops.index.put(record.name, record.id);
                    if (record.id > maxId)
                        maxId = record.id;
                }
                break;
            }
        }
        shops.nextId = maxId + 1;
        return shops;
    }


}
