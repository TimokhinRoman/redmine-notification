package ru.arriah.redminenotification.util;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class CommandProcessor {

   private final Map<String, CommandExecutor> executors;

   public CommandProcessor(List<CommandExecutor> executors) {
      this.executors = CollectionUtils.toMap(executors, CommandExecutor::getCommand);
   }

   public void process(String command, String... params) {
      CommandExecutor executor = executors.get(command);

      if (executor == null) {
         log.info("Unknown command: " + command);
         return;
      }

      executor.execute(params);
   }

   protected CommandExecutor getExecutor(String command) {
      return executors.get(command);
   }
}
