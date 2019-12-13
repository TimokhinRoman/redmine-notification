package ru.arriah.redminenotification.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import ru.arriah.redminenotification.telegram.entity.Message;
import ru.arriah.redminenotification.telegram.entity.Update;
import ru.arriah.redminenotification.telegram.request.MessageRequest;
import ru.arriah.redminenotification.telegram.response.MessageResponse;
import ru.arriah.redminenotification.telegram.response.UpdateResponse;
import ru.arriah.redminenotification.util.RequestBuilderExecutor;

import java.util.List;

public class TelegramServiceImpl implements TelegramService {

   private final RequestBuilderExecutor<TelegramUrlBuilder> executor;

   @Autowired
   public TelegramServiceImpl(RequestBuilderExecutor<TelegramUrlBuilder> executor) {
      this.executor = executor;
   }

   public List<Update> getUpdates() {
      return executor.<UpdateResponse>builder()
            .forUrl(urlBuilder -> urlBuilder.getUpdates().toUrl())
            .forClass(UpdateResponse.class)
            .getForEntity()
            .getBody()
            .getUpdates(); // TODO: wrap it for logging and nullity check
   }

   public Message sendMessage(MessageRequest request) {
      return executor.<MessageResponse>builder()
            .forUrl(TelegramUrlBuilder::sendMessage)
            .forEntity(request)
            .forMethod(HttpMethod.POST)
            .forClass(MessageResponse.class)
            .exchange()
            .getBody()
            .getMessage(); // TODO: wrap it for logging and nullity check
   }
}
