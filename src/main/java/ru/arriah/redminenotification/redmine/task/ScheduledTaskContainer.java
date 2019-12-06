package ru.arriah.redminenotification.redmine.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public class ScheduledTaskContainer {

   private final Map<Class, ScheduledFuture> tasks;

   public ScheduledTaskContainer() {
      tasks = new HashMap<>();
   }

   public <T extends Schedulable> void put(Class<T> taskType, ScheduledFuture future) {
      tasks.put(taskType, future);
   }

   public <T extends Schedulable> ScheduledFuture get(Class<T> taskType) {
      return tasks.get(taskType);
   }
}
