package ru.arriah.redminenotification.redmine;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import ru.arriah.redminenotification.auth.AuthenticationManager;
import ru.arriah.redminenotification.util.HttpEntityFactory;

@Component
public class RedmineHttpEntityFactory implements HttpEntityFactory {

   private final AuthenticationManager authManager;

   public RedmineHttpEntityFactory(AuthenticationManager authManager) {
      this.authManager = authManager;
   }

   @Override
   public <T> HttpEntity<T> getHttpEntity() {
      return new HttpEntity<>(getRequiredHeaders());
   }

   @Override
   public <T> HttpEntity<T> getHttpEntity(T body) {
      return new HttpEntity<>(body, getRequiredHeaders());
   }

   private HttpHeaders getRequiredHeaders() {
      HttpHeaders headers = new HttpHeaders();
      headers.set("X-Redmine-API-Key", authManager.getCurrentUser().getApiKey());
      return headers;
   }
}
