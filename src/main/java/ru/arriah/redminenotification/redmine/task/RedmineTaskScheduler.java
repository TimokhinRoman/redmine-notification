package ru.arriah.redminenotification.redmine.task;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import ru.arriah.redminenotification.auth.AuthenticationEvent;
import ru.arriah.redminenotification.auth.UserToken;
import ru.arriah.redminenotification.util.Table;

import java.util.concurrent.ScheduledFuture;

@Component
@Slf4j
public class RedmineTaskScheduler implements ApplicationListener<AuthenticationEvent> {

   private final TaskScheduler taskScheduler;
   private final RedmineTaskFactory taskFactory;
   private final Table<String, Class<? extends Schedulable>, ScheduledFuture> userTasks;

   public RedmineTaskScheduler(TaskScheduler taskScheduler, RedmineTaskFactory taskFactory) {
      this.taskScheduler = taskScheduler;
      this.taskFactory = taskFactory;
      this.userTasks = new Table<>();
   }

   @Override
   public void onApplicationEvent(@NotNull AuthenticationEvent authenticationEvent) {
      UserToken userToken = authenticationEvent.getUserToken();
      String apiKey = userToken.getApiKey();
      if (userTasks.containsKey(apiKey)) return;

      runTaskForUser(MonitorAssignedIssuesTask.class, userToken);
   }

   public <T extends Schedulable> void runTaskForUser(Class<T> taskType, UserToken userToken) {
      Schedulable task = taskFactory.createTaskForUser(taskType, userToken);
      ScheduledFuture<?> future = taskScheduler.scheduleWithFixedDelay(task, task.getDelay());
      storeTask(task, future, userToken.getApiKey());
   }

   public <T extends Schedulable> void stopTaskForUser(Class<T> taskType, UserToken userToken) {
      String key = userToken.getApiKey();
      if (!userTasks.containsKey(key)) return;

      ScheduledFuture future = userTasks.get(key, taskType);
      if (future != null) {
         future.cancel(false);
      }
   }

   private <T extends Schedulable> void storeTask(T task, ScheduledFuture future, String key) {
      userTasks.put(key, task.getClass(), future);
   }
}
