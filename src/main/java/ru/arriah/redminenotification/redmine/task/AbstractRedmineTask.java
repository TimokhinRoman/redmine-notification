package ru.arriah.redminenotification.redmine.task;

import ru.arriah.redminenotification.auth.NotAuthenticatedException;
import ru.arriah.redminenotification.auth.UserToken;
import ru.arriah.redminenotification.redmine.RedmineService;
import ru.arriah.redminenotification.redmine.TelegramIssueService;
import ru.arriah.redminenotification.redmine.diff.IssueDifference;
import ru.arriah.redminenotification.redmine.diff.IssueDifferenceCalculator;
import ru.arriah.redminenotification.redmine.entity.Issue;

import java.util.List;

abstract class AbstractRedmineTask implements Schedulable {

   final RedmineTaskIssuesHolder issuesHolder;
   final RedmineService redmine;
   final TelegramIssueService telegram;

   // injected by task factory
   private UserToken user;

   AbstractRedmineTask(RedmineTaskIssuesHolder issuesHolder, RedmineService redmine, TelegramIssueService telegram) {
      this.issuesHolder = issuesHolder;
      this.redmine = redmine;
      this.telegram = telegram;
   }

   void setUser(UserToken user) {
      this.user = user;
   }

   @Override
   public void run() {
      if (user == null) throw new NotAuthenticatedException();

      List<Issue> oldIssues = issuesHolder.getIssues(user, getClass());
      List<Issue> newIssues = getIssues();
      issuesHolder.putIssues(user, getClass(), newIssues);
      List<IssueDifference> diffs = IssueDifferenceCalculator.diff(oldIssues, newIssues);
      if (!diffs.isEmpty()) {
         for (IssueDifference diff : diffs) {
            telegram.sendIssue(user.getChatId(), diff.getFirstNonNullIssue());
         }
      }
   }

   abstract List<Issue> getIssues();
}
