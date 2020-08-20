package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.logging.Logger;

@Path("beverage")
public class BeverageResource {

    private static final Logger logger = Logger.getLogger("BeverageResource");

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBeverages(@DefaultValue("0") @QueryParam("page") int page,
                                 @DefaultValue("1000") @QueryParam("pageSize") int pageSize,
                                 @DefaultValue("0") @QueryParam("minPrice")double minPrice,
                                 @DefaultValue("1000") @QueryParam("maxPrice") double maxPrice,
                                 @QueryParam("query") String query) throws IOException {

        if (page < 0 || pageSize < 0 || minPrice < 0 || maxPrice < 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Parameters value can not be negative")).build();
        }

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:9999/v1/beverage/all");
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        logger.info("Status Code - Get Status" +response.getStatus());
        String entityResponse = response.readEntity(String.class);
        logger.info("List of All Beverages:" + entityResponse);

        client.close();
        return Response
                .status(Response.Status.OK)
                .entity(entityResponse)
                .build();
    }
}
