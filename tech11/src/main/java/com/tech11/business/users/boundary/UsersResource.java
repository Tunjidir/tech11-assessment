package com.tech11.business.users.boundary;

import com.tech11.business.users.entity.User;
import java.net.URI;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.stream.JsonCollectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * This class acts a JAX-RS resource exposing it's endpoints via REST and JSON
 *
 * @author olatunji oniyide
 */
@Path("/users")
@Produces({APPLICATION_JSON, APPLICATION_XML})
@Consumes({APPLICATION_JSON, APPLICATION_XML})
public class UsersResource {
    
    @Inject
    ProductiveAdmin admin;
    
    @POST
    public Response addUser(JsonObject request, @Context UriInfo info) {
        final User user = this.admin.addUser(new User(request));
        final URI location = info.getAbsolutePathBuilder().path("/" + user.getId()).build();
        return Response.created(location).build();
    }
    
    @GET
    public JsonArray getAllUsers() {
        return this.admin.allUsers().
                stream().
                map(User::toJSON).
                collect(JsonCollectors.toJsonArray());
    }
    
    @GET
    @Path("/{id}")
    public JsonObject singleUser(@PathParam("id") Long id) {
        return this.admin.findUser(id).toJSON();
    }
    
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, JsonObject request) {
        final User updatedUser = this.admin.updateUser(id, new User(request));
        return Response.ok().entity(updatedUser.toJSON()).build();
    }
    
}
