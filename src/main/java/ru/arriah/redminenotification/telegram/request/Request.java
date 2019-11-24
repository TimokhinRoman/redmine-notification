package ru.arriah.redminenotification.telegram.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Request {

   @JsonProperty("chat_id")
   protected final String chatId;
}
