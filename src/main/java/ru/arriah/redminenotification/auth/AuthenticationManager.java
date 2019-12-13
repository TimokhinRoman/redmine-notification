package ru.arriah.redminenotification.auth;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuthenticationManager {

   private final ApplicationEventPublisher publisher;
   private final Map<String, UserToken> userMap;
   private final ThreadLocal<UserToken> currentUser;

   public AuthenticationManager(ApplicationEventPublisher publisher) {
      this.publisher = publisher;
      this.userMap = new ConcurrentHashMap<>();
      this.currentUser = new ThreadLocal<>();
   }

   public UserToken authenticate(UserToken user) {
      if (!isValid(user)) throw new AuthenticationException(user);
      userMap.put(user.getChatId(), user);
      publisher.publishEvent(new AuthenticationEvent(this, user));
      return user;
   }

   public boolean isAuthenticated(UserToken user) {
      String key = user != null ? user.getChatId() : null;
      return isAuthenticated(key);
   }

   public boolean isAuthenticated(String key) {
      if (key == null) return false;
      return userMap.containsKey(key);
   }

   public boolean isCurrentUserAuthenticated() {
      return isAuthenticated(getCurrentUser());
   }

   public UserToken getCurrentUser() {
      return Optional.ofNullable(currentUser.get())
            .orElse(UserToken.EMPTY);
   }

   public void setCurrentUser(UserToken currentUser) {
      this.currentUser.set(currentUser);
   }

   public void setCurrentUser(String key) {
      UserToken user = getAuthenticatedUser(key);
      if (user == UserToken.EMPTY) {
         user = UserToken.builder()
               .chatId(key)
               .build();
      }
      setCurrentUser(user);
   }

   public UserToken getAuthenticatedUser(String key) {
      if (key == null) return UserToken.EMPTY;
      return userMap.getOrDefault(key, UserToken.EMPTY);
   }

   private boolean isValid(UserToken user) {
      return user != null
            && user.getChatId() != null
            && user.getApiKey() != null;
   }
}
