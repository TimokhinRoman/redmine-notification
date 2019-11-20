package ru.arriah.redminenotification.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Component
@Scope("prototype")
public class TelegramUrlBuilder {

   private final UriComponentsBuilder builder;

   public TelegramUrlBuilder(@Value("${telegram.url}") String url, @Value("${telegram.token}") String token) {
      Objects.requireNonNull(token);
      builder = UriComponentsBuilder.fromHttpUrl(url)
            .path("/bot" + token);
   }

   public TelegramUrlBuilder getUpdates() {
      builder.path("/getUpdates");
      return this;
   }

   public TelegramUrlBuilder sendMessage() {
      builder.path("/sendMessage");
      return this;
   }

   public String toUrl() {
      return builder.toUriString();
   }
}
