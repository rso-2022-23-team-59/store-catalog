package si.fri.rso.storecatalog.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.storecatalog.lib.Store;
import si.fri.rso.storecatalog.services.beans.StoreBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/stores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StoreResource {

    private Logger log = Logger.getLogger(StoreResource.class.getName());

    @Inject
    private StoreBean storeBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get all stores.", summary = "Get all stores")
    @APIResponses({
        @APIResponse(
            responseCode = "200",
            description = "List of stores",
            content = @Content(schema = @Schema(implementation = Store.class, type = SchemaType.ARRAY)),
            headers = {@Header(name = "X-Total-Count", description = "Number of stores in list")}
        )
    })
    @GET
    public Response getAllStores() {
        List<Store> stores = storeBean.getStoreFilter(uriInfo);
        return Response.ok(stores).header("X-Total-Count", stores.size()).build();
    }

    @Operation(description = "Get data for a single store.", summary = "Get data for a single store")
    @APIResponses({
        @APIResponse(
            responseCode = "200",
            description = "Single store information.",
            content = @Content(schema = @Schema(implementation = Store.class))
        ),
        @APIResponse(
            responseCode = "404",
            description = "Store could not be found."
        ),
    })
    @GET
    @Path("/{storeId}")
    public Response getSingleStore(@Parameter(description = "Store ID.", required = true)
                                     @PathParam("storeId") Integer storeId) {

        Store store = storeBean.getStore(storeId);
        if (store == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(store).build();
    }

    @Operation(description = "Insert new store.", summary = "Insert new store")
    @APIResponses({
        @APIResponse(
            responseCode = "201",
            description = "Store successfully added.",
            content = @Content(schema = @Schema(implementation = Store.class))
        ),
        @APIResponse(
            responseCode = "400",
            description = "The store could not be inserted. Incorrect or missing parameters."
        ),
    })
    @POST
    public Response createStore(@RequestBody(
            description = "Object with store information.",
            required = true, content = @Content(
            schema = @Schema(implementation = Store.class))) Store store) {

        if ((store.getName() == null || store.getDescription() == null || store.getUrl() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            store = storeBean.createStore(store);
        }

        return Response.status(Response.Status.CREATED).entity(store).build();

    }


    @Operation(description = "Update single store.", summary = "Update store")
    @APIResponses({
        @APIResponse(
            responseCode = "204",
            description = "Store successfully updated.",
            content = @Content(schema = @Schema(implementation = Store.class))
        ),
        @APIResponse(
            responseCode = "404",
            description = "Store could not be found."
        )
    })
    @PUT
    @Path("{storeId}")
    public Response putStore(@Parameter(description = "Store ID.", required = true)
                               @PathParam("storeId") Integer storeId,
                               @RequestBody(
                                       description = "DTO object with store information.",
                                       required = true, content = @Content(
                                       schema = @Schema(implementation = Store.class)))
                               Store store) {

        store = storeBean.putStore(storeId, store);

        if (store == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(store).build();
    }

    @Operation(description = "Delete store.", summary = "Delete store")
    @APIResponses({
        @APIResponse(
            responseCode = "204",
            description = "Store successfully deleted."
        ),
        @APIResponse(
            responseCode = "404",
            description = "Store could not be found."
        )
    })
    @DELETE
    @Path("{storeId}")
    public Response deleteStore(@Parameter(description = "Store ID.", required = true)
                                  @PathParam("storeId") Integer storeId) {

        boolean deleted = storeBean.deleteStore(storeId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


}
