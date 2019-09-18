package com.tech11.business.users.boundary;

import javax.ejb.ApplicationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author olatunji oniyide
 */
@ApplicationException(rollback = true)
public class UserNotFoundException extends WebApplicationException {
    
    public UserNotFoundException(String message) {
        super(Response.status(Status.NOT_FOUND).header("reason", message).build());
    }
}
