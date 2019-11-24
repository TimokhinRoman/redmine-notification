package ru.arriah.redminenotification.telegram.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Response {

   @JsonProperty("ok")
   protected boolean ok;
}
