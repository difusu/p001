package jettyserver.rest;

/**
 * Created by difu on 06/27/2017.
 */

import jettyserver.data.Address;
import jettyserver.data.CoffeeShops;
import jettyserver.data.LocationRecord;
import org.json.simple.parser.ParseException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/shops")
public class Shops {
    @GET
    @Path("/id/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("param") String msg) {

        int id = Integer.parseInt(msg);
        try {
            LocationRecord record = CoffeeShops.get().getById(id);
            return build(record);
        } catch (IOException e) {
            return Response.status(503).build();
        }
    }

    @GET
    @Path("/name/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByName(@PathParam("param") String name) {

        try {
            LocationRecord record = CoffeeShops.get().getByName(name);
            return build(record);
        } catch (IOException e) {
            return Response.status(503).build();
        }
    }


    @GET
    @Path("/address/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findNear(@PathParam("param") String address) {

        try {
            LocationRecord target = Address.getLocation(address);
            if (address == null) {
                return Response.status(400).build();
            }
            LocationRecord record = CoffeeShops.get().findNear(target);
            return build(record);
        } catch (IOException | ParseException e) {
            return Response.status(503).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@FormParam("name") String name,
                        @FormParam("address") String address,
                        @FormParam("lat") String lat,
                        @FormParam("lng") String lng) {
        try {
            CoffeeShops shops = CoffeeShops.get();
            Integer id = shops.add(name, address, lat, lng);
            if (id == null) {
                return Response.status(409).build();
            }
            return Response.status(200).entity("{\"id\": " + id + "}").build();
        } catch (IOException e) {
            return Response.status(503).build();
        }
    }


    @PUT
    @Path("/id/{msg}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response update(@PathParam("msg") String msg,
                           @FormParam("name") String name,
                           @FormParam("address") String address,
                           @FormParam("lat") String lat,
                           @FormParam("lng") String lng) {
        try {
            CoffeeShops shops = CoffeeShops.get();
            LocationRecord record = shops.update(msg, name, address, lat, lng);
            if (record == null) {
                return Response.status(404).build();
            }
            return build(record);
        } catch (IOException e) {
            return Response.status(503).build();
        }
    }

    @DELETE
    @Path("/id/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("param") String msg) {

        Integer id = Integer.parseInt(msg);
        try {
            boolean found = CoffeeShops.get().delete(id);
            return Response.status(found? 200 : 404).build();
        } catch (IOException e) {
            return Response.status(503).build();
        }
    }


    private Response build(LocationRecord record) {
        if (record == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(record.toJson()).build();
    }

}

