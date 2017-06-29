package jettyserver.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by difu on 06/28/2017.
 */
public class AddressTest {

    @Test
    public void testGetLocation() throws Exception {
        String address = "765 Oregon Ave, 94303";
        LocationRecord expected = new LocationRecord(address, 37.437884, -122.131670);
        LocationRecord record = Address.getLocation("765 Oregon Ave, 94303");
        Assert.assertEquals(expected.toString(), record.toString());
    }

}