package ru.arriah.redminenotification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.arriah.redminenotification.redmine.RedmineService;
import ru.arriah.redminenotification.redmine.task.MonitorAssignedIssuesTask;
import ru.arriah.redminenotification.telegram.TelegramService;

@Configuration
public class TaskConfig {

   @Bean
   @Scope("prototype")
   public MonitorAssignedIssuesTask monitorAssignedIssuesTask(RedmineService redmine, TelegramService telegram) {
      return new MonitorAssignedIssuesTask(redmine, telegram);
   }
}
