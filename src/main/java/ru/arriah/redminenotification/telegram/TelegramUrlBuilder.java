package ru.arriah.redminenotification.telegram;

import org.springframework.web.util.UriComponentsBuilder;
import ru.arriah.redminenotification.util.UrlBuilder;

import java.util.Objects;

public class TelegramUrlBuilder implements UrlBuilder {

   private final UriComponentsBuilder builder;

   public TelegramUrlBuilder(String url, String token) {
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
