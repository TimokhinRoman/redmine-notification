package ru.arriah.redminenotification.auth;

public class AuthenticationException extends RuntimeException {

   private final UserToken userToken;

   public AuthenticationException(UserToken userToken) {
      this.userToken = userToken;
   }

   public AuthenticationException(String message, UserToken userToken) {
      super(message);
      this.userToken = userToken;
   }
}
