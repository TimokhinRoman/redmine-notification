package ru.arriah.redminenotification.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;
import java.util.function.Function;

public class RequestBuilderExecutor<B extends UrlBuilder> {

   private final RestTemplate restTemplate;
   private final UrlBuilderFactory<B> urlBuilderFactory;
   private final HttpEntityFactory httpEntityFactory;

   public RequestBuilderExecutor(RestTemplate restTemplate, UrlBuilderFactory<B> urlBuilderFactory, HttpEntityFactory httpEntityFactory) {
      this.restTemplate = restTemplate;
      this.urlBuilderFactory = urlBuilderFactory;
      this.httpEntityFactory = httpEntityFactory;
   }

   public class Builder<R> {

      private final B urlBuilder;
      private HttpMethod httpMethod;
      private HttpEntity requestEntity;
      private Class<R> responseType;

      private Builder() {
         this.urlBuilder = urlBuilderFactory.newBuilder();
      }

      public Builder<R> forUrl(Consumer<B> consumer) {
         consumer.accept(urlBuilder);
         return this;
      }

      public Builder<R> forMethod(HttpMethod httpMethod) {
         this.httpMethod = httpMethod;
         return this;
      }

      public Builder<R> forEntity(Function<HttpEntityFactory, HttpEntity> consumer) {
         this.requestEntity = consumer.apply(httpEntityFactory);
         return this;
      }

      public Builder<R> forEntity(Object body) {
         requestEntity = new HttpEntity<>(body);
         return this;
      }

      public Builder<R> forClass(Class<R> responseType) {
         this.responseType = responseType;
         return this;
      }

      public ResponseEntity<R> exchange() {
         return restTemplate.exchange(urlBuilder.toUrl(), httpMethod, requestEntity, responseType);
      }

      public ResponseEntity<R> getForEntity() {
         return restTemplate.getForEntity(urlBuilder.toUrl(), responseType);
      }
   }

   public <T> Builder<T> builder() {
      return new Builder<>();
   }
}
