package de.uniba.dsg.jaxrs.resources;

import com.google.gson.internal.$Gson$Preconditions;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.logging.Logger;

@Path("beverage/bottles")
public class BottleResource {

    private static final Logger logger = Logger.getLogger("BottleResource");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBottles(@DefaultValue("0") @QueryParam("page") int page,
                               @DefaultValue("1000") @QueryParam("pageSize") int pageSize,
                               @DefaultValue("0") @QueryParam("minPrice")double minPrice,
                               @DefaultValue("1000") @QueryParam("maxPrice") double maxPrice,
                               @QueryParam("query") String query) throws IOException {

        if (page < 0 || pageSize <0 || minPrice <0 || maxPrice <0) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Parameters value can not be negative")).build();
        }

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:9999/v1/bottle/all");
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        logger.info("Status Code - Get Status   "+response.getStatus());
        String entityResponse = response.readEntity(String.class);
        logger.info("List of All bottles:" +entityResponse);

        client.close();
        return Response
                .status(Response.Status.OK)
                .entity(entityResponse)
                .build();
    }
}
