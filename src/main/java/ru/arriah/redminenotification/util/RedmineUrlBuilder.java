package ru.arriah.redminenotification.util;

import org.springframework.web.util.UriComponentsBuilder;

public class RedmineUrlBuilder implements UrlBuilder {

   private final UriComponentsBuilder builder;

   public RedmineUrlBuilder(String url) {
      System.out.println("RedmineUrlBuilder constructed");
      builder = UriComponentsBuilder.fromHttpUrl(url);
   }

   public RedmineUrlBuilder issues() {
      builder.path("/issues");
      return this;
   }

   public RedmineUrlBuilder json() {
      builder.path(".json");
      return this;
   }

   public RedmineUrlBuilder assignedTo(String id) {
      builder.queryParam("assigned_to_id", id);
      return this;
   }

   public String toUrl() {
      return builder.toUriString();
   }
}
