package ru.arriah.redminenotification.telegram;

import ru.arriah.redminenotification.telegram.entity.Message;
import ru.arriah.redminenotification.telegram.entity.Update;
import ru.arriah.redminenotification.telegram.request.MessageRequest;

import java.util.List;

public interface TelegramService {

   List<Update> getUpdates();

   default Message sendMessage(String chatId, String message) {
      return sendMessage(MessageRequest.builder().chatId(chatId).text(message).build());
   }

   Message sendMessage(MessageRequest request);
}
