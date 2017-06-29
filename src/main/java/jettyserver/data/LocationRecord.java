package jettyserver.data;

import java.io.File;
import java.io.IOException;

import org.apache.commons.csv.*;
import org.json.simple.JSONObject;

public class LocationRecord {
    public int id;
    public String name;
    public String address;
    double lat;
    double longt;

    public LocationRecord(String address, double lat, double longt) {
        this.id = -1;
        this.name = "";
        this.address = address;
        this.lat = lat;
        this.longt = longt;
    }

    public LocationRecord(int id, String name, String address, double lat, double longt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.longt = longt;
    }

    public LocationRecord(CSVRecord record) {
        id = Integer.parseInt(record.get(0));
        name = record.get(1).trim();
        address = record.get(2).trim();
        lat = Double.parseDouble(record.get(3));
        longt = Double.parseDouble(record.get(4));
    }

    public double distance(LocationRecord other) {
        double x = lat - other.lat;
        double y = longt - other.longt;
        return x * x + y * y;
    }

    @Override
    public String toString() {
        return String.format("id=%d, name=%s, address=%s, lat=%f, longt=%f", id, name, address, lat, longt);
    }

    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", name);
        json.put("address", address);
        json.put("lat", lat);
        json.put("lng", longt);
        return json.toJSONString();
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof LocationRecord))
            return false;
        LocationRecord other = (LocationRecord) o;
        return id==other.id && name.equals(other.name) && address.equals(other.address) && lat==other.lat && longt==other.longt;
    }
}
