package ru.arriah.redminenotification.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RequestLoggingInterceptor implements ClientHttpRequestInterceptor {

   private final Logger log = LoggerFactory.getLogger(this.getClass());

   @Override
   public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
      logRequest(request, body);
      return execution.execute(request, body);
   }

   private void logRequest(HttpRequest request, byte[] body) throws IOException {
      log.info("============================================request begin============================================");
      log.info("URI         : {}", request.getURI());
      log.info("Method      : {}", request.getMethod());
      log.info("Headers     : {}", request.getHeaders());
      log.info("Request body: {}", new String(body, "UTF-8"));
      log.info("=============================================request end=============================================");
   }
}