package ru.arriah.redminenotification.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.arriah.redminenotification.logging.RequestLoggingInterceptor;
import ru.arriah.redminenotification.redmine.RedmineUrlBuilder;
import ru.arriah.redminenotification.telegram.TelegramUrlBuilder;
import ru.arriah.redminenotification.util.*;

import java.util.List;

@Configuration
public class ApplicationConfig {

   @Bean
   public RestTemplate getRestTemplate() {
      return new RestTemplateBuilder()
            .interceptors(new RequestLoggingInterceptor())
            .build();
   }

   @Bean
   public UrlBuilderFactory<RedmineUrlBuilder> redmineUrlBuilderFactory(@Value("${redmine.url}") String url) {
      return () -> new RedmineUrlBuilder(url);
   }

   @Bean
   public UrlBuilderFactory<TelegramUrlBuilder> telegramUrlBuilderFactory(@Value("${telegram.url}") String url,
                                                                          @Value("${telegram.token}") String token) {
      return () -> new TelegramUrlBuilder(url, token);
   }

   @Bean
   public HttpEntityFactory httpEntityFactory(@Value("${redmine.apiKey}") String apiKey) {
      return new HttpEntityFactory(apiKey);
   }

   @Bean
   public RequestBuilderExecutor<RedmineUrlBuilder> redmineRequestBuilderExecutor(RestTemplate restTemplate,
                                                                                  UrlBuilderFactory<RedmineUrlBuilder> urlBuilderFactory,
                                                                                  HttpEntityFactory httpEntityFactory) {
      return new RequestBuilderExecutor<>(restTemplate, urlBuilderFactory, httpEntityFactory);
   }

   @Bean
   public RequestBuilderExecutor<TelegramUrlBuilder> telegramRequestBuilderExecutor(RestTemplate restTemplate,
                                                                                    UrlBuilderFactory<TelegramUrlBuilder> urlBuilderFactory,
                                                                                    HttpEntityFactory httpEntityFactory) {
      return new RequestBuilderExecutor<>(restTemplate, urlBuilderFactory, httpEntityFactory);
   }

   @Bean()
   @Autowired(required = false)
   public CommandProcessor commandProcessor(List<CommandExecutor> executors) {
      return new CommandProcessor(executors);
   }
}
