package ru.arriah.redminenotification.telegram.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.arriah.redminenotification.telegram.TelegramService;
import ru.arriah.redminenotification.telegram.entity.Update;
import ru.arriah.redminenotification.util.CommandProcessor;

import java.util.HashSet;

@Slf4j
@Component
public class TelegramListenTask {

   private final TelegramService telegram;
   private final CommandProcessor processor;
   private final HashSet<Integer> processedUpdates;

   @Autowired
   public TelegramListenTask(TelegramService telegram, CommandProcessor processor) {
      this.telegram = telegram;
      this.processor = processor;
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
         processor.process(update.getMessage().getText());
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
}
