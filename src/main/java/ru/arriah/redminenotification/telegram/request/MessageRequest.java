package ru.arriah.redminenotification.telegram.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageRequest extends Request {

   @JsonProperty("text")
   private final String text;

   @Builder
   private MessageRequest(String chatId, String text) {
      super(chatId);
      this.text = text;
   }
}
