package ru.arriah.redminenotification.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.arriah.redminenotification.redmine.Issue;
import ru.arriah.redminenotification.service.RedmineService;
import ru.arriah.redminenotification.service.TelegramService;
import ru.arriah.redminenotification.telegram.Update;
import ru.arriah.redminenotification.telegram.request.MessageRequest;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Component
public class TelegramListenTask {

   private final TelegramService telegram;
   private final RedmineService redmine;
   private final String chatId;

   private final HashSet<Integer> processedUpdates;

   @Autowired
   public TelegramListenTask(TelegramService telegram, RedmineService redmine, @Value("${telegram.chatId}") String chatId) {
      this.telegram = telegram;
      this.redmine = redmine;
      this.chatId = chatId;
      this.processedUpdates = new HashSet<>();
   }

   @Scheduled(fixedDelayString = "${telegram.task.delay}")
   public void listen() {
      for (Update update : telegram.getUpdates()) {
         try {
            processUpdate(update);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   private void processUpdate(Update update) {
      if (processedUpdates.contains(update.getId())) return;

      log.info("Processing: " + update);

      if (update.hasMessage()) {
         switch (update.getMessage().getText()) {
            case "/issues":
               processIssuesCommand();
               break;
            default:
               log.info("Unknown command");
               break;
         }
      } else {
         log.info("Update has no message, ignore");
      }

      processedUpdates.add(update.getId());
   }

   private void processIssuesCommand() {
      List<Issue> issues = redmine.getIssuesAssignedToMe();
      for (Issue issue : issues) {
         telegram.sendMessage(MessageRequest.builder().chatId(chatId).text(issue.toString()).build());
      }

   }
}
