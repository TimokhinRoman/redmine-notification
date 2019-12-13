package ru.arriah.redminenotification.telegram.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageRequest extends Request {

   @JsonProperty("text")
   private final String text;
   @JsonProperty("parse_mode")
   private final String parseMode;

   @Builder
   private MessageRequest(String chatId, String text, String parseMode) {
      super(chatId);
      this.text = text;
      this.parseMode = parseMode;
   }
}
