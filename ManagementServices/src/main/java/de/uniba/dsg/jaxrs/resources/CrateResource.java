package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;
import de.uniba.dsg.jaxrs.model.logic.Crate;

import javax.ws.rs.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("crate")
public class CrateResource {
    private static final Logger logger = Logger.getLogger("CrateResource");

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCrates(@DefaultValue("0") @QueryParam("page") int page,
                              @DefaultValue("1000") @QueryParam("pageSize") int pageSize,
                              @DefaultValue("0") @QueryParam("minPrice")double minPrice,
                              @DefaultValue("1000") @QueryParam("maxPrice") double maxPrice,
                              @QueryParam("query") String query){
        if (page < 0 || pageSize <0 || minPrice <0 || maxPrice <0) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ErrorMessage(ErrorType.INVALID_PARAMETER, "Parameters value can not be negative")).build();
        }
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:9999/v1/crate/all");
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        System.out.println("Status Code - Get Status   "+response.getStatus());
        String entityResponse = response.readEntity(String.class);
        logger.info("All crates:  "+entityResponse);
        client.close();
        return Response
                .status(Response.Status.OK)
                .entity(entityResponse)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCrate(Crate crateToCreate){

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:9999/v1/crate");
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response
                = invocationBuilder
                .post(Entity.entity(crateToCreate, MediaType.APPLICATION_JSON));
        System.out.println("Status Code - Create Status   "+response.getStatus());

        String entityResponse = response.readEntity(String.class);
        logger.info("All crates:  "+entityResponse);
        client.close();
        return Response
                .status(Response.Status.CREATED)
                .entity(entityResponse)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCrate(Crate crateToUpdate, @DefaultValue("1") @PathParam("id") final int id){
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:9999/v1/crate/"+id);
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response
                = invocationBuilder
                .put(Entity.entity(crateToUpdate, MediaType.APPLICATION_JSON));
        System.out.println("Status Code - Update Status   "+response.getStatus());
        String entityResponse = response.readEntity(String.class);
        logger.info("All crates:  "+entityResponse);
        client.close();
        return Response
                .status(Response.Status.OK)
                .entity(entityResponse)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCrate(@DefaultValue("1") @PathParam("id") final int id){
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:9999/v1/crate/"+id);
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response
                = invocationBuilder
                 .delete();
        System.out.println("Status Code - Delete Status   "+response.getStatus());
        logger.info("Deleted crate  ");
        response.close();
        client.close();
        return Response
                .status(Response.Status.OK)
                .build();
    }

}
