package ru.arriah.redminenotification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.arriah.redminenotification.telegram.Message;
import ru.arriah.redminenotification.telegram.request.MessageRequest;
import ru.arriah.redminenotification.telegram.response.MessageResponse;
import ru.arriah.redminenotification.telegram.response.UpdateResponse;
import ru.arriah.redminenotification.telegram.Update;
import ru.arriah.redminenotification.util.TelegramUrlBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramService {

   private final RestTemplate restTemplate;
   private final TelegramUrlBuilder urlBuilder;

   @Autowired
   public TelegramService(RestTemplate restTemplate, TelegramUrlBuilder urlBuilder) {
      this.restTemplate = restTemplate;
      this.urlBuilder = urlBuilder;
   }

   public List<Update> getUpdates() {
      String url = urlBuilder.getUpdates().toUrl();
      ResponseEntity<UpdateResponse> responseEntity = restTemplate.getForEntity(url, UpdateResponse.class);
      if (responseEntity.getBody() == null) return new ArrayList<>();
      return responseEntity.getBody().getUpdates();
   }

   public Message sendMessage(MessageRequest request) {
      String url = urlBuilder.sendMessage().toUrl();
      HttpEntity<MessageRequest> requestEntity = new HttpEntity<>(request);
      ResponseEntity<MessageResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, MessageResponse.class);
      if (responseEntity.getBody() == null) return null;
      return responseEntity.getBody().getMessage();
   }
}
