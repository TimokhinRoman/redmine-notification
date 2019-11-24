package ru.arriah.redminenotification.telegram.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.arriah.redminenotification.redmine.entity.Issue;
import ru.arriah.redminenotification.redmine.RedmineService;
import ru.arriah.redminenotification.telegram.TelegramService;
import ru.arriah.redminenotification.telegram.entity.Update;
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
      if (isUpdateAlreadyProcessed(update)) return;

      log.info("Processing: " + update);

      if (update.hasMessage()) {
         processCommand(update.getMessage().getText());
      } else {
         log.info("Update has no message, ignore");
      }

      markUpdateAsProcessed(update);
   }

   private boolean isUpdateAlreadyProcessed(Update update) {
      return processedUpdates.contains(update.getId());
   }

   private void markUpdateAsProcessed(Update update) {
      processedUpdates.add(update.getId());
   }

   private void processCommand(String command) {
      switch (command) {
         case "/issues":
            processIssuesCommand();
            break;
         default:
            log.info("Unknown command");
            break;
      }
   }

   private void processIssuesCommand() {
      List<Issue> issues = redmine.getIssuesAssignedToMe();
      for (Issue issue : issues) {
         telegram.sendMessage(MessageRequest.builder().chatId(chatId).text(issue.toString()).build());
      }

   }
}
