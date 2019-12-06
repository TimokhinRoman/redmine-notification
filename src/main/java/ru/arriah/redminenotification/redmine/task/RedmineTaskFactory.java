package ru.arriah.redminenotification.redmine.task;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;
import ru.arriah.redminenotification.auth.AuthenticationManager;
import ru.arriah.redminenotification.auth.UserToken;

@Component
public class RedmineTaskFactory {

   private final BeanFactory factory;
   private final AuthenticationManager authManager;

   public RedmineTaskFactory(BeanFactory factory, AuthenticationManager authManager) {
      this.factory = factory;
      this.authManager = authManager;
   }

   public <T extends Schedulable> Schedulable createTaskForUser(Class<T> taskType, UserToken userToken) {
      T task = factory.getBean(taskType);
      return wrapTask(task, userToken);
   }

   private Schedulable wrapTask(Schedulable task, UserToken userToken) {
      if (task == null) return task;
      return new Wrapper(task, userToken);
   }

   private class Wrapper implements Schedulable {

      private final Schedulable task;
      private final UserToken userToken;

      private Wrapper(Schedulable task, UserToken userToken) {
         this.task = task;
         this.userToken = userToken;
      }

      @Override
      public int getDelay() {
         return task.getDelay();
      }

      @Override
      public void run() {
         authManager.setCurrentUser(userToken);
         task.run();
         authManager.setCurrentUser((UserToken) null);
      }
   }
}
