
package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.Controllers.BottleService;
import de.uniba.dsg.jaxrs.model.api.PaginatedBottles;
import de.uniba.dsg.jaxrs.model.dto.BottleDTO;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;
import de.uniba.dsg.jaxrs.model.logic.Bottle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Logger;

@Path("bottle")

public class DbHandlerBottle {

    private static final Logger logger = Logger.getLogger("DataBaseResource");
    JSONParser jsonParser = new JSONParser();

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBottles(@Context final UriInfo info,
                               @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                               @QueryParam("page") @DefaultValue("1") final int page) {

        logger.info("Get all Beverages. Pagination parameters: page-" + page + " pageLimit-" + pageLimit);

        if (pageLimit < 1 || page < 1) {

            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();

        }

        final PaginationHelper<Bottle> helper = new PaginationHelper<>(BottleService.instance.getAllBeverages());
        final PaginatedBottles response = new PaginatedBottles(helper.getPagination(info, page, pageLimit),
                BottleDTO.marshall(helper.getPaginatedList(), info.getBaseUri()), info.getRequestUri());
        return Response.ok(response).build();

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)

    public Response getBottle(
            @Context final UriInfo info,
            @PathParam("id") @Min(value = 1) final int id)
    {
        logger.info(String.format("Get Bottle with id=%d", id));

        Bottle bottle = BottleService.instance.getBottleById(id);
        if (bottle == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Bottle not found")).build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(new BottleDTO(bottle))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBottle(BottleDTO newBottle) {
        if (newBottle == null) {
            logger.info("Ticket not found   " + Response.Status.NOT_FOUND);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        Bottle bottles = newBottle.MapToBottle();
        Bottle bottle = BottleService.instance.createBottle(bottles);
        logger.info("Bottle created: " + bottle);
        try {

            Object obj = jsonParser.parse(new FileReader("bottle.json"));
            JSONObject jsonObj = new JSONObject();
            JSONArray jsonArray = (JSONArray) obj;
            jsonObj.put("id", bottle.getId());
            jsonObj.put("name", bottle.getName());
            jsonObj.put("volume", bottle.getVolume());
            jsonObj.put("isAlcoholic", bottle.getisAlcoholic());
            jsonObj.put("volumePercent", bottle.getVolumePercent());
            jsonObj.put("price", bottle.getPrice());
            jsonObj.put("supplier", bottle.getSupplier());
            jsonObj.put("inStock", bottle.getInStock());
            jsonObj.put("beverageType", bottle.getBeverageType().toString());
            jsonArray.add(jsonObj);

            FileWriter file = new FileWriter("bottle.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();

        } catch (Exception E) {
            E.printStackTrace();
        }

        return Response
                .status(Response.Status.CREATED)
                .entity(new BottleDTO(bottle))
//                .type(MediaType.APPLICATION_JSON)
                .build();
    }


    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response updateBottle(BottleDTO newBottle, @PathParam("id") final int id) {

        if (newBottle == null) {
            logger.severe("Invalid request body   " + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Invalid request body", Response.Status.BAD_REQUEST); //400 status code
        }

        Bottle bottles = newBottle.MapToBottle();
        Bottle updatedBottle = BottleService.instance.updateBottle(bottles, id);

        if (updatedBottle == null) {
            logger.warning("Bottle not found with ID: " + id + "    " + Response.Status.NOT_FOUND);
            throw new WebApplicationException("Bottle not found with ID: " + id, 404);
        }
        logger.info("Bottle updated: " + updatedBottle);

        try {
            Object obj = jsonParser.parse(new FileReader("bottle.json"));
            JSONArray jsonArray = (JSONArray) obj;
            //JSONObject jsonObjPut= new JSONObject();

            for (int i = 0; i < jsonArray.size(); i++) {
                org.json.JSONObject jsonObj = new org.json.JSONObject(jsonArray.get(i).toString());
                if (jsonObj.getInt("id") == id) {
                    jsonArray.remove(jsonArray.get(i)); //remove previous data
                    jsonObj.put("id", updatedBottle.getId());
                    jsonObj.put("name", updatedBottle.getName());
                    jsonObj.put("volume", updatedBottle.getVolume());
                    jsonObj.put("isAlcoholic", updatedBottle.getisAlcoholic());
                    jsonObj.put("volumePercent", updatedBottle.getVolumePercent());
                    jsonObj.put("price", updatedBottle.getPrice());
                    jsonObj.put("supplier", updatedBottle.getSupplier());
                    jsonObj.put("inStock", updatedBottle.getInStock());
                    jsonObj.put("beverageType", updatedBottle.getBeverageType().toString());

                    jsonArray.add(jsonObj);
                    break;
                }

            }

            FileWriter file = new FileWriter("bottle.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();

        } catch (Exception E) {
            E.printStackTrace();
        }


        return Response
                .status(Response.Status.OK)
                .entity(new BottleDTO(updatedBottle))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")

    public Response deleteBottle(@PathParam("id") final int id) {

        Bottle bottleToDelete = BottleService.instance.deleteBottle(id);
        if (bottleToDelete == null) {
            logger.info("Bottle not found with ID: " + id + "    " + Response.Status.NOT_FOUND);
            throw new WebApplicationException("Bottle not found with ID: " + id, 404);
        }
        logger.info("Bottle deleted: " + bottleToDelete);
        try {
            Object obj = jsonParser.parse(new FileReader("bottle.json"));
            org.json.JSONObject jsonObj;
            JSONArray jsonArray = (JSONArray) obj;

            for (int i = 0; i < jsonArray.size(); i++) {
                jsonObj = new org.json.JSONObject(jsonArray.get(i).toString());
                if (jsonObj.getInt("id") == id) {
                    jsonArray.remove(jsonArray.get(i));
                }
            }

            FileWriter file = new FileWriter("bottle.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();

        } catch (Exception E) {
            E.printStackTrace();
        }

        return Response
                .status(Response.Status.OK)
                .entity("Bottle is deleted")
                .build();
    }



}