package de.codemonaut.aws.note;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Note {

  @Inject
  @ConfigProperty(defaultValue = "No note created yet.", name="content")
  String content;

  public String getContent() {
    return this.content;
  }

  public void setContent(String note) {
    this.content = note;
  }

}
