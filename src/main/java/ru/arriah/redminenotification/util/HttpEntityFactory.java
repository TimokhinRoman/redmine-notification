package ru.arriah.redminenotification.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class HttpEntityFactory {

   private final String apiKey;

   @Autowired
   public HttpEntityFactory(@Value("${redmine.apiKey}") String apiKey) {
      this.apiKey = apiKey;
   }

   public <T> HttpEntity<T> getHttpEntity() {
      return getHttpEntity(null);
   }

   public <T> HttpEntity<T> getHttpEntity(@Nullable T body) {
      return new HttpEntity<>(body, getRequiredHeaders());
   }

   private HttpHeaders getRequiredHeaders() {
      HttpHeaders headers = new HttpHeaders();
      headers.set("X-Redmine-API-Key", apiKey);
      return headers;
   }
}
