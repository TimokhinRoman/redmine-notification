package ru.arriah.redminenotification.redmine.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true, exclude = {"description"})
public class Issue extends Entity {

   @JsonProperty("author")
   private User author;
   @JsonProperty("assigned_to")
   private User assignedTo;
   @JsonProperty("created_on")
   private LocalDateTime createdOn;
   @JsonProperty("subject")
   private String subject;
   @JsonProperty("description")
   private String description;
   @JsonProperty("fixed_version")
   private Version version;

}
