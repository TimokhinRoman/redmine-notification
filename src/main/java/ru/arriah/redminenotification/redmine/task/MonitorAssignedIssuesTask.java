package ru.arriah.redminenotification.redmine.task;

import lombok.extern.slf4j.Slf4j;
import ru.arriah.redminenotification.redmine.RedmineService;
import ru.arriah.redminenotification.redmine.TelegramIssueService;
import ru.arriah.redminenotification.redmine.entity.Issue;

import java.util.List;

@Slf4j
public class MonitorAssignedIssuesTask extends AbstractRedmineTask {


   public MonitorAssignedIssuesTask(RedmineTaskIssuesHolder issuesHolder, RedmineService redmine, TelegramIssueService telegram) {
      super(issuesHolder, redmine, telegram);
   }

   @Override
   public long getDelay() {
      return 5000;
   }

   @Override
   List<Issue> getIssues() {
      return redmine.getIssuesAssignedTo("30");
   }
}
