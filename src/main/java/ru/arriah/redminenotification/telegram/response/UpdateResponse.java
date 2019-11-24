package ru.arriah.redminenotification.telegram.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.arriah.redminenotification.telegram.entity.Update;

import java.util.List;

@Getter
@Setter
public class UpdateResponse extends Response {

   @JsonProperty("result")
   private List<Update> updates;

}
