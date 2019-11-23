package ru.arriah.redminenotification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import ru.arriah.redminenotification.telegram.Message;
import ru.arriah.redminenotification.telegram.Update;
import ru.arriah.redminenotification.telegram.request.MessageRequest;
import ru.arriah.redminenotification.telegram.response.MessageResponse;
import ru.arriah.redminenotification.telegram.response.UpdateResponse;
import ru.arriah.redminenotification.util.RequestBuilderExecutor;
import ru.arriah.redminenotification.util.TelegramUrlBuilder;

import java.util.List;

@Service
public class TelegramService {

   private final RequestBuilderExecutor<TelegramUrlBuilder> executor;

   @Autowired
   public TelegramService(RequestBuilderExecutor<TelegramUrlBuilder> executor) {

      this.executor = executor;
   }

   public List<Update> getUpdates() {
      return executor.<UpdateResponse>builder()
            .forUrl(urlBuilder -> urlBuilder.getUpdates().toUrl())
            .forClass(UpdateResponse.class)
            .getForEntity()
            .getBody()
            .getUpdates();
   }

   public Message sendMessage(MessageRequest request) {
      return executor.<MessageResponse>builder()
            .forUrl(TelegramUrlBuilder::sendMessage)
            .forEntity(request)
            .forMethod(HttpMethod.POST)
            .forClass(MessageResponse.class)
            .exchange()
            .getBody()
            .getMessage();
   }
}
