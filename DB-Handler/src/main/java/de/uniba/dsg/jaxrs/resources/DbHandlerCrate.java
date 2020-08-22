package de.uniba.dsg.jaxrs.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.uniba.dsg.jaxrs.Controllers.BottleService;
import de.uniba.dsg.jaxrs.Controllers.CrateService;
import de.uniba.dsg.jaxrs.model.api.PaginatedCrates;
import de.uniba.dsg.jaxrs.model.dto.BottleDTO;
import de.uniba.dsg.jaxrs.model.dto.CrateDTO;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;
import de.uniba.dsg.jaxrs.model.logic.Bottle;
import de.uniba.dsg.jaxrs.model.logic.Crate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;
import java.util.logging.Logger;

@Path("crate")

public class DbHandlerCrate {

    private static final Logger logger = Logger.getLogger("DataBaseResource");
    JSONParser jsonParser = new JSONParser();

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)

    public Response getCrates(@Context final UriInfo info,
                              @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                              @QueryParam("page") @DefaultValue("1") final int page) {

        logger.info("Get all Crates. Pagination parameters: page-" + page + " pageLimit-" + pageLimit);

        if (pageLimit < 1 || page < 1) {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        final PaginationHelper<Crate> helper = new PaginationHelper<>(CrateService.instance.getAllBeverages());
        final PaginatedCrates response = new PaginatedCrates(helper.getPagination(info, page, pageLimit),
                CrateDTO.marshall(helper.getPaginatedList(), info.getBaseUri()), info.getRequestUri());
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON })

    public Response getCrate(
            @Context final UriInfo info,
            @PathParam("id") @Min(value = 1) final int id)
    {
        logger.info(String.format("Get Crate with id=%d", id));

        Crate crate = CrateService.instance.getCrateById(id);
        if (crate == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Crate not found")).build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(new CrateDTO(crate))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Response createCrate(CrateDTO newCrate) {

        if (newCrate == null) {
            logger.info("Ticket not found" + Response.Status.NOT_FOUND);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }


        Crate crates = newCrate.MapToCrate();
        Crate crate = CrateService.instance.createCrate(crates);
        logger.info("Crate created: "+ crate);

        try {

            Object obj = jsonParser.parse(new FileReader("crate.json"));
            JSONObject jsonObj = new JSONObject();
            JSONArray jsonArray = (JSONArray) obj;
            jsonObj.put("id", crate.getId());

            Bottle tempBottle = crate.getBottle();

            JSONObject jsonObjBottle = new JSONObject();
            jsonObjBottle.put("id", tempBottle.getId());
            jsonObjBottle.put("inStock", tempBottle.getInStock());
            jsonObjBottle.put("volumePercent", tempBottle.getVolumePercent());
            jsonObjBottle.put("volume", tempBottle.getVolume());
            jsonObjBottle.put("name", tempBottle.getName());
            jsonObjBottle.put("supplier", tempBottle.getSupplier());
            jsonObjBottle.put("price", tempBottle.getPrice());
            jsonObjBottle.put("isAlcoholic", tempBottle.getisAlcoholic());
            jsonObj.put("bottle", jsonObjBottle);
            jsonObj.put("noOfBottles", crate.getNoOfBottles());
            jsonObj.put("price", crate.getPrice());
            jsonObj.put("inStock", crate.getInStock());
            jsonArray.add(jsonObj);

            FileWriter file = new FileWriter("crate.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();

        } catch (Exception E) {
            E.printStackTrace();
        }

        return Response
                .status(Response.Status.CREATED)
                .entity(new CrateDTO(crate))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response updateCrate(CrateDTO newCrate, @PathParam("id") final int id) {

        if (newCrate == null) {
            logger.severe("Invalid request body   " + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Invalid request body", Response.Status.BAD_REQUEST); //400 status code
        }

        Crate crates = newCrate.MapToCrate();
        Crate updatedCrate = CrateService.instance.updateCrate(crates, id);

        if (updatedCrate == null) {
            logger.warning("Crate not found with ID: " + id + "    " + Response.Status.NOT_FOUND);
            throw new WebApplicationException("Crate not found with ID: " + id, 404);
        }
        logger.info("Crate updated: " + updatedCrate);

        try {
            Object obj = jsonParser.parse(new FileReader("crate.json"));
            JSONArray jsonArray = (JSONArray) obj;
            //JSONObject jsonObjPut= new JSONObject();

            for (int i = 0; i < jsonArray.size(); i++) {
                org.json.JSONObject jsonObj = new org.json.JSONObject(jsonArray.get(i).toString());
                if (jsonObj.getInt("id") == id) {
                    jsonArray.remove(jsonArray.get(i)); //remove previous data
                    jsonObj.put("id", updatedCrate.getId());
                    Bottle tempBottle = updatedCrate.getBottle();

                    JSONObject jsonObjBottle = new JSONObject();
                    jsonObjBottle.put("id", tempBottle.getId());
                    jsonObjBottle.put("inStock", tempBottle.getInStock());
                    jsonObjBottle.put("volumePercent", tempBottle.getVolumePercent());
                    jsonObjBottle.put("volume", tempBottle.getVolume());
                    jsonObjBottle.put("name", tempBottle.getName());
                    jsonObjBottle.put("supplier", tempBottle.getSupplier());
                    jsonObjBottle.put("price", tempBottle.getPrice());
                    jsonObjBottle.put("isAlcoholic", tempBottle.getisAlcoholic());
                    jsonObj.put("bottle", jsonObjBottle);
                    jsonObj.put("noOfBottles", updatedCrate.getNoOfBottles());
                    jsonObj.put("price", updatedCrate.getPrice());
                    jsonObj.put("inStock", updatedCrate.getInStock());

                    jsonArray.add(jsonObj);
                    break;
                }

            }

            FileWriter file = new FileWriter("crate.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();

        } catch (Exception E) {
            E.printStackTrace();
        }


        return Response
                .status(Response.Status.OK)
                .entity(new CrateDTO(updatedCrate))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")

    public Response deleteCrate(@PathParam("id") final int id) {

        Crate crateToDelete = CrateService.instance.deleteCrate(id);
        if (crateToDelete == null) {
            logger.info("Crate not found with ID: " + id + "    " + Response.Status.NOT_FOUND);
            throw new WebApplicationException("Crate not found with ID: " + id, 404);
        }
        logger.info("Crate deleted: " + crateToDelete);
        try {
            Object obj = jsonParser.parse(new FileReader("crate.json"));
            org.json.JSONObject jsonObj;
            JSONArray jsonArray = (JSONArray) obj;

            for (int i = 0; i < jsonArray.size(); i++) {
                jsonObj = new org.json.JSONObject(jsonArray.get(i).toString());
                if (jsonObj.getInt("id") == id) {
                    jsonArray.remove(jsonArray.get(i));
                }
            }

            FileWriter file = new FileWriter("crate.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();

        } catch (Exception E) {
            E.printStackTrace();
        }

        return Response
                .status(Response.Status.OK)
                .entity("Crate is deleted")
                .build();
    }
}
