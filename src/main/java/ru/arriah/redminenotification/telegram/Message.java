package ru.arriah.redminenotification.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

   @JsonProperty("message_id")
   private int id;
   @JsonProperty("text")
   private String text;
}
