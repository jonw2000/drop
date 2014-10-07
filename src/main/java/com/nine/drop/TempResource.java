package com.nine.drop;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/temp")
public class TempResource {

    @Produces("text/plain")
    @GET
    public String getValue() {
        return "some value";
    }
}
