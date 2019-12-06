package ru.arriah.redminenotification.redmine.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import ru.arriah.redminenotification.auth.AuthenticationEvent;
import ru.arriah.redminenotification.auth.UserToken;
import ru.arriah.redminenotification.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
@Slf4j
public class RedmineTaskScheduler implements ApplicationListener<AuthenticationEvent> {

   private final TaskScheduler taskScheduler;
   private final RedmineTaskFactory taskFactory;
   private final Map<String, ScheduledTaskContainer> userTasks;

   public RedmineTaskScheduler(TaskScheduler taskScheduler, RedmineTaskFactory taskFactory) {
      this.taskScheduler = taskScheduler;
      this.taskFactory = taskFactory;
      this.userTasks = new HashMap<>();
   }

   @Override
   public void onApplicationEvent(AuthenticationEvent authenticationEvent) {
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

      ScheduledTaskContainer container = userTasks.get(key);
      ScheduledFuture future = container.get(taskType);
      if (future != null) {
         future.cancel(false);
      }
   }

   private <T extends Schedulable> void storeTask(T task, ScheduledFuture future, String key) {
      ScheduledTaskContainer container = getOrCreateNewContainer(key);
      container.put(task.getClass(), future);
   }

   private ScheduledTaskContainer getOrCreateNewContainer(String key) {
      return CollectionUtils.computeIfAbsent(userTasks, key, ScheduledTaskContainer::new);
   }
}
