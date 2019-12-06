package ru.arriah.redminenotification.redmine.task;

import lombok.extern.slf4j.Slf4j;
import ru.arriah.redminenotification.auth.AuthenticationManager;
import ru.arriah.redminenotification.redmine.RedmineService;
import ru.arriah.redminenotification.redmine.entity.Issue;
import ru.arriah.redminenotification.telegram.TelegramService;

import java.util.List;

@Slf4j
public class MonitorAssignedIssuesTask extends AbstractRedmineTask {

   protected MonitorAssignedIssuesTask(AuthenticationManager authManager, RedmineTaskIssuesHolder issuesHolder,
                                       RedmineService redmine, TelegramService telegram) {
      super(authManager, issuesHolder, redmine, telegram);
   }

   @Override
   public int getDelay() {
      return 5;
   }

   @Override
   List<Issue> getIssues() {
      return redmine.getIssuesAssignedToMe();
   }
}
