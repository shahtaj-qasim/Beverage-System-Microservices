package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.Controllers.BeverageService;
import de.uniba.dsg.jaxrs.model.Beverage;
import de.uniba.dsg.jaxrs.model.api.PaginatedBeverages;
import de.uniba.dsg.jaxrs.model.dto.BeverageDTO;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Logger;

@Path("beverage")
public class DbHandlerBeverage {

    private static final Logger logger = Logger.getLogger("DataBaseResource");

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

        final PaginationHelper<Beverage> helper = new PaginationHelper<>(BeverageService.instance.getAllBeverages());
        final PaginatedBeverages response = new PaginatedBeverages(helper.getPagination(info, page, pageLimit),
                new BeverageDTO().marshall(helper.getPaginatedList(), info.getBaseUri()), info.getRequestUri());
        return Response.ok(response).build();
    }

}
