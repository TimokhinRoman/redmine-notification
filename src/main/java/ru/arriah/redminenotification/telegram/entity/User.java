package ru.arriah.redminenotification.telegram.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {

   @JsonProperty("id")
   private String id;
   @JsonProperty("first_name")
   private String firstName;
   @JsonProperty("last_name")
   private String lastName;
   @JsonProperty("username")
   private String userName;
}
