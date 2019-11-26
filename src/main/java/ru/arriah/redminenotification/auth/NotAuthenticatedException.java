package ru.arriah.redminenotification.auth;

public class NotAuthenticatedException extends RuntimeException {

   private final UserToken userToken;

   public NotAuthenticatedException(UserToken userToken) {
      this.userToken = userToken;
   }

   @Override
   public String getMessage() {
      return String.valueOf(userToken);
   }
}
