package ru.arriah.redminenotification.telegram.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest extends Request {

   @JsonProperty("text")
   private String text;

}
