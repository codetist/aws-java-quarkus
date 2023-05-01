package de.codemonaut.aws.note;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

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
