package jettyserver.data;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by difu on 06/28/2017.
 */
public class CoffeeShopsTest {
    @Test
    public void testGet() throws IOException {
        CoffeeShops shops = CoffeeShops.get();
        Assert.assertNotNull(shops);
        for (LocationRecord r : shops.getLocations().values()) {
            System.out.println(r.toString());
        }
        Assert.assertEquals(48, shops.getLocations().size());
        String name = "Equator Coffees & Teas";
        Assert.assertEquals(name, shops.getById(1).name);
        Assert.assertEquals(name, shops.getByName(name).name);
    }

}