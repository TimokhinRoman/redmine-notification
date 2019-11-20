package ru.arriah.redminenotification.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class Update {

   @JsonProperty("update_id")
   private int id;
   @JsonProperty("message")
   private Message message;


   public boolean hasMessage() {
      return message != null && !StringUtils.isEmpty(message.getText());
   }
}
