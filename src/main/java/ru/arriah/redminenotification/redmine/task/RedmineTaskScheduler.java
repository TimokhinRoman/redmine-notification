package ru.arriah.redminenotification.redmine.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import ru.arriah.redminenotification.auth.AuthenticationEvent;

@Component
@Slf4j
public class RedmineTaskScheduler implements ApplicationListener<AuthenticationEvent> {

   private final TaskScheduler taskScheduler;

   public RedmineTaskScheduler(TaskScheduler taskScheduler) {
      this.taskScheduler = taskScheduler;
   }

   @Override
   public void onApplicationEvent(AuthenticationEvent authenticationEvent) {
      log.info("TODO: run redmine tasks for authenticated user");
   }
}
