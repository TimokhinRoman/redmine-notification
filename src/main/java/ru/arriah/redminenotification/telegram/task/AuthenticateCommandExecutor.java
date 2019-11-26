package ru.arriah.redminenotification.telegram.task;

import org.springframework.stereotype.Component;
import ru.arriah.redminenotification.auth.AuthenticationManager;
import ru.arriah.redminenotification.auth.UserToken;
import ru.arriah.redminenotification.util.CommandExecutor;

@Component
public class AuthenticateCommandExecutor implements CommandExecutor {

   private final AuthenticationManager authManager;

   public AuthenticateCommandExecutor(AuthenticationManager authManager) {
      this.authManager = authManager;
   }

   @Override
   public String getCommand() {
      return "/key";
   }

   @Override
   public void execute(String[] params) {
      //if (authManager.isCurrentUserAuthenticated()) return;

      UserToken user = authManager.getCurrentUser();
      if (params.length > 0) {
         user = UserToken.builder()
               .chatId(user.getChatId())
               .apiKey(params[0])
               .build();
      }
      user = authManager.authenticate(user);
      authManager.setCurrentUser(user);
   }
}
