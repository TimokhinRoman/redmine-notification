package ru.arriah.redminenotification.auth;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AuthenticationEvent extends ApplicationEvent {

   private final UserToken userToken;

   public AuthenticationEvent(Object source, UserToken userToken) {
      super(source);
      this.userToken = userToken;
   }
}
