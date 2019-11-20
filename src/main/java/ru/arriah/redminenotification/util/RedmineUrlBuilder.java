package ru.arriah.redminenotification.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Scope("prototype")
public class RedmineUrlBuilder {

   private final UriComponentsBuilder builder;

   @Autowired
   public RedmineUrlBuilder(@Value("${redmine.url}") String url) {
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
