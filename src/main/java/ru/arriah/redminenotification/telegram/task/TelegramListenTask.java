package ru.arriah.redminenotification.telegram.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.arriah.redminenotification.auth.AuthenticationManager;
import ru.arriah.redminenotification.auth.NotAuthenticatedException;
import ru.arriah.redminenotification.telegram.TelegramService;
import ru.arriah.redminenotification.telegram.entity.Update;
import ru.arriah.redminenotification.util.CommandProcessor;

import java.util.Arrays;
import java.util.HashSet;

@Slf4j
@Component
public class TelegramListenTask {

   private final TelegramService telegram;
   private final CommandProcessor processor;
   private final AuthenticationManager authManager;
   private final HashSet<Integer> processedUpdates;

   @Autowired
   public TelegramListenTask(TelegramService telegram, CommandProcessor processor, AuthenticationManager authManager) {
      this.telegram = telegram;
      this.processor = processor;
      this.authManager = authManager;
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

      try {
         if (update.hasMessage()) {
            authManager.setCurrentUser(update.getMessage().getUser().getId());
            processMessage(update.getMessage().getText());
         } else {
            log.info("Update has no message, ignore");
         }
      } catch (Exception e) {
         log.error("Failed to process an update", e);
      }

      markUpdateAsProcessed(update);
   }

   private boolean isUpdateAlreadyProcessed(Update update) {
      return processedUpdates.contains(update.getId());
   }

   private void processMessage(String message) {
      try {
         String[] strings = message.split(" ");
         String command = strings[0];
         String[] params = Arrays.copyOfRange(strings, 1, strings.length);
         processor.process(command, params);
      } catch (NotAuthenticatedException e) {
         telegram.sendMessage(authManager.getCurrentUser().getChatId(), "API key есть? А если найду?");
      } catch (Exception e) {
         telegram.sendMessage(authManager.getCurrentUser().getChatId(), "Возникла непредвиденная ошибка.");
         throw e;
      }
   }

   private void markUpdateAsProcessed(Update update) {
      processedUpdates.add(update.getId());
   }
}
