package ru.arriah.redminenotification.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;

public class HttpEntityFactory {

   private final String apiKey;

   public HttpEntityFactory(String apiKey) {
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
