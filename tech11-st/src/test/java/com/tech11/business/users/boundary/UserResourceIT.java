package com.tech11.business.users.boundary;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author olatunji oniyide
 */
public class UserResourceIT {

    private Client client;
    private WebTarget usersResourceTarget;
    private static final String RUT_URI = "http://localhost:8080/tech11/resources/users";

    @Before
    public void initClient() {
        this.client = ClientBuilder.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        this.usersResourceTarget = client.target(RUT_URI);
    }

    @Test
    public void getAllUsers() {
        final Response register = registerUser();
        JsonArray response = this.usersResourceTarget.request(APPLICATION_JSON).get(JsonArray.class);
        assertNotNull(response);
    }

    @Test
    public void addUser() {
        final Response response = registerUser();
        assertNotNull(response);
        String location = response.getLocation().toString();
        assertNotNull(location);
    }
    
    @Test
    public void findSingleUser() {
        final Response response = registerUser();
        final String uri = response.getLocation().toString();
        final JsonObject searchResponse = this.client.target(uri)
                .request(APPLICATION_JSON)
                .get(JsonObject.class);
        String firstname = searchResponse.getString("firstname");
        assertEquals("Olatunji", firstname);
    }
    
    @Test
    public void updateExistingUser() {
        final Response response = registerUser();
        final String uri = response.getLocation().toString();
        final Response updateResponse = this.client.target(uri).request().put(Entity.json(mockUpdate()));
        final JsonObject updatedUser = updateResponse.readEntity(JsonObject.class);
        assertEquals("Matthias", updatedUser.getString("firstname"));  
    }
    
    @Test
    public void unknownUserNotFound() {
        final String unknownURI = "http://localhost:8080/tech11/resources/users/1234";
        final Response response = this.client.target(unknownURI).request().get();
        assertNotNull(response.getHeaderString("reason"));
    }

    public Response registerUser() {
        return this.usersResourceTarget.request(APPLICATION_JSON).post(Entity.json(mockUser()));
    }

    public JsonObject mockUser() {
        return Json.createObjectBuilder().add("firstname", "Olatunji")
                .add("lastname", "Oniyide")
                .add("email", "Olatunji4you@gmail.com")
                .add("password", "igotthis")
                .add("birthday", LocalDate.of(1995, 8, 1).toString())
                .build();
    }
    
    public JsonObject mockUpdate() {
        return Json.createObjectBuilder().add("firstname", "Matthias")
                .add("lastname", "Reining")
                .add("email", "MatthiasReining@tech11.com")
                .add("password", "mattrocks")
                .add("birthday", LocalDate.of(1980, 8, 1).toString())
                .build();
    }
    
    @After
    public void tearDown() {
        if(this.client != null) this.client.close();
    }
}
