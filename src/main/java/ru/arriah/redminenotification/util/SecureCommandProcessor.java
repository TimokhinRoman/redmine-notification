package ru.arriah.redminenotification.util;

import ru.arriah.redminenotification.auth.AuthenticationManager;
import ru.arriah.redminenotification.auth.NotAuthenticatedException;

import java.util.List;

public class SecureCommandProcessor extends CommandProcessor {

   private final AuthenticationManager authManager;

   public SecureCommandProcessor(List<CommandExecutor> executors, AuthenticationManager authManager) {
      super(executors);
      this.authManager = authManager;
   }

   @Override
   public void process(String command, String... params) {
      CommandExecutor executor = getExecutor(command);
      if (isSecured(executor) && !authManager.isCurrentUserAuthenticated()) {
         throw new NotAuthenticatedException(authManager.getCurrentUser());
      }

      super.process(command, params);
   }

   private boolean isSecured(CommandExecutor executor) {
      return executor != null && executor.getClass().isAnnotationPresent(Secured.class);
   }
}
