package ru.arriah.redminenotification.telegram.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class Update {

   @JsonProperty("update_id")
   private int id;
   @JsonProperty("message")
   private Message message;


   public boolean hasMessage() {
      return message != null && !StringUtils.isEmpty(message.getText());
   }
}
