package ru.arriah.redminenotification.telegram.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.arriah.redminenotification.telegram.entity.Message;

@Getter
@Setter
public class MessageResponse extends Response {

   @JsonProperty("result")
   private Message message;
}
