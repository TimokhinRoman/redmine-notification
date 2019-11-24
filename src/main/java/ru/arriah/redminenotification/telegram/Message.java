package ru.arriah.redminenotification.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Message {

   @JsonProperty("message_id")
   private int id;
   @JsonProperty("text")
   private String text;
   @JsonProperty("chat")
   private Chat chat;
}
