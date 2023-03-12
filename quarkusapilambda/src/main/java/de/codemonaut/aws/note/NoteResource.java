package de.codemonaut.aws.note;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("note")
@ApplicationScoped
public class NoteResource {

  @Inject
  Note note;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String get() {
    return note.getContent();
  }

  @POST
  @Consumes(MediaType.TEXT_PLAIN)
  public void post(String content) {
    note.setContent(content);
  }
}
