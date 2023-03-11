package de.codemonaut.aws.note;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static java.lang.System.Logger.Level.INFO;

@ApplicationScoped
public class Note {

  static System.Logger log = System.getLogger(Note.class.getName());

  @Inject
  @ConfigProperty(defaultValue = "No note created yet.", name="content")
  String content;

  public String getContent() {
    log.log(INFO, "Content=", content);
    return this.content;
  }

  public void setContent(String note) {
    this.content = note;
  }

}
