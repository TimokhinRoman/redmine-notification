package ru.arriah.redminenotification.telegram.task;

import org.springframework.stereotype.Component;
import ru.arriah.redminenotification.auth.AuthenticationManager;
import ru.arriah.redminenotification.redmine.RedmineService;
import ru.arriah.redminenotification.redmine.entity.Issue;
import ru.arriah.redminenotification.telegram.TelegramService;
import ru.arriah.redminenotification.util.CommandExecutor;
import ru.arriah.redminenotification.util.Secured;

import java.util.List;

@Component
@Secured
public class IssuesCommandExecutor implements CommandExecutor {

   private final TelegramService telegram;
   private final RedmineService redmine;
   private final AuthenticationManager authManager;

   public IssuesCommandExecutor(TelegramService telegram, RedmineService redmine, AuthenticationManager authManager) {
      this.telegram = telegram;
      this.redmine = redmine;
      this.authManager = authManager;
   }

   @Override
   public String getCommand() {
      return "/issues";
   }

   @Override
   public void execute(String[] params) {
      List<Issue> issues = redmine.getIssuesAssignedToMe();
      String chatId = authManager.getCurrentUser().getChatId();

      if (issues.isEmpty()) {
         telegram.sendMessage(chatId, "Список задач пуст.");
         return;
      }

      for (Issue issue : issues) {
         telegram.sendMessage(chatId, issue.toString());
      }
   }
}
