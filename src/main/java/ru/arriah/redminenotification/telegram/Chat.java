package ru.arriah.redminenotification.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Chat {

   @JsonProperty("id")
   private int id;
}
