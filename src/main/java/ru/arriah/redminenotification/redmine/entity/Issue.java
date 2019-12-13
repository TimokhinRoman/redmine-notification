package ru.arriah.redminenotification.redmine.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString(callSuper = true, exclude = {"description"})
public class Issue extends Entity {

   @JsonProperty("project")
   private NamedEntity project;
   @JsonProperty("tracker")
   private NamedEntity tracker;
   @JsonProperty("status")
   private NamedEntity status;
   @JsonProperty("priority")
   private NamedEntity priority;
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
   private NamedEntity version;
   private Map<Integer, CustomField> customFields;

   @JsonProperty("custom_fields")
   public void setCustomFieldList(List<CustomField> customFields) {
      if (customFields != null) {
         this.customFields = customFields.stream()
               .collect(Collectors.toMap(CustomField::getId, Function.identity()));
      }
   }

   @NotNull
   public Map<Integer, CustomField> getCustomFields() {
      if (customFields == null) customFields = new HashMap<>();
      return customFields;
   }
}
