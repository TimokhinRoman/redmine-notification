package ru.arriah.redminenotification.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class CommandProcessor {

   private final Map<String, CommandExecutor> executors;

   public CommandProcessor(List<CommandExecutor> executors) {
      if (executors == null) {
         this.executors = Collections.emptyMap();
      } else {
         this.executors = executors.stream()
               .collect(Collectors.toMap(CommandExecutor::getCommand, Function.identity()));
      }
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
