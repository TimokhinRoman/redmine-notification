package ru.arriah.redminenotification.redmine;

import org.springframework.stereotype.Service;
import ru.arriah.redminenotification.redmine.entity.Issue;
import ru.arriah.redminenotification.telegram.TelegramService;
import ru.arriah.redminenotification.telegram.entity.Message;
import ru.arriah.redminenotification.telegram.entity.Update;
import ru.arriah.redminenotification.telegram.request.MessageRequest;

import java.util.List;

@Service
public class TelegramIssueServiceImpl implements TelegramIssueService {

   private final TelegramService telegram;
   private final IssueViewGenerator generator;

   public TelegramIssueServiceImpl(TelegramService telegram, IssueViewGenerator generator) {
      this.telegram = telegram;
      this.generator = generator;
   }

   @Override
   public List<Update> getUpdates() {
      return telegram.getUpdates();
   }

   @Override
   public Message sendMessage(MessageRequest request) {
      return telegram.sendMessage(request);
   }

   @Override
   public Message sendIssue(String chatId, Issue issue) {
      return telegram.sendMessage(MessageRequest.builder()
            .chatId(chatId)
            .text(generator.getView(issue))
            .parseMode("html")
            .build());
   }
}
