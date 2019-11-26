package ru.arriah.redminenotification.auth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserToken {

   public static final UserToken EMPTY = UserToken.builder().build();

   private String chatId;
   private String apiKey;
}
