package ru.arriah.redminenotification.telegram.task;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.arriah.redminenotification.redmine.RedmineService;
import ru.arriah.redminenotification.redmine.entity.Issue;
import ru.arriah.redminenotification.telegram.TelegramService;
import ru.arriah.redminenotification.telegram.request.MessageRequest;
import ru.arriah.redminenotification.util.CommandExecutor;

import java.util.List;

@Component
public class IssuesCommandExecutor implements CommandExecutor {

   private final TelegramService telegram;
   private final RedmineService redmine;
   private final String chatId;

   public IssuesCommandExecutor(TelegramService telegram, RedmineService redmine, @Value("${telegram.chatId}") String chatId) {
      this.telegram = telegram;
      this.redmine = redmine;
      this.chatId = chatId;
   }

   @Override
   public String getCommand() {
      return "/issues";
   }

   @Override
   public void execute() {
      List<Issue> issues = redmine.getIssuesAssignedToMe();
      for (Issue issue : issues) {
         telegram.sendMessage(MessageRequest.builder().chatId(chatId).text(issue.toString()).build());
      }
   }
}
