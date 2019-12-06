package ru.arriah.redminenotification.redmine.task;

import ru.arriah.redminenotification.auth.AuthenticationManager;
import ru.arriah.redminenotification.auth.NotAuthenticatedException;
import ru.arriah.redminenotification.auth.UserToken;
import ru.arriah.redminenotification.redmine.RedmineService;
import ru.arriah.redminenotification.redmine.diff.IssueDifference;
import ru.arriah.redminenotification.redmine.diff.IssueDifferenceCalculator;
import ru.arriah.redminenotification.redmine.entity.Issue;
import ru.arriah.redminenotification.telegram.TelegramService;

import java.util.List;

abstract class AbstractRedmineTask implements Schedulable {

   final AuthenticationManager authManager;
   final RedmineTaskIssuesHolder issuesHolder;
   final RedmineService redmine;
   final TelegramService telegram;

   protected AbstractRedmineTask(AuthenticationManager authManager, RedmineTaskIssuesHolder issuesHolder,
                                 RedmineService redmine, TelegramService telegram) {
      this.authManager = authManager;
      this.issuesHolder = issuesHolder;
      this.redmine = redmine;
      this.telegram = telegram;
   }

   @Override
   public void run() {
      UserToken user = authManager.getCurrentUser();
      if (user == null) throw new NotAuthenticatedException();

      List<Issue> oldIssues = issuesHolder.getIssues(user, this.getClass());
      List<Issue> newIssues = getIssues();
      List<IssueDifference> diff = IssueDifferenceCalculator.diff(oldIssues, newIssues);
      if (!diff.isEmpty()) {
         telegram.sendMessage(user.getChatId(), "");
      }
   }

   abstract List<Issue> getIssues();
}
