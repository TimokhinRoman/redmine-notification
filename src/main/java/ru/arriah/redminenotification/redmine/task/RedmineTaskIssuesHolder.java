package ru.arriah.redminenotification.redmine.task;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.arriah.redminenotification.auth.UserToken;
import ru.arriah.redminenotification.redmine.entity.Issue;
import ru.arriah.redminenotification.util.Table;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedmineTaskIssuesHolder {

   private final Table<String, Class<? extends AbstractRedmineTask>, List<Issue>> issues;

   public RedmineTaskIssuesHolder() {
      issues = new Table<>(ConcurrentHashMap::new, ConcurrentHashMap::new);
   }

   @NotNull
   public <T extends AbstractRedmineTask> List<Issue> getIssues(UserToken userToken, Class<T> taskType) {
      return issues.getOrDefault(userToken.getApiKey(), taskType, Collections.emptyList());
   }

   @Nullable
   public <T extends AbstractRedmineTask> List<Issue> putIssues(UserToken userToken, Class<T> taskType, List<Issue> issues) {
      return this.issues.put(userToken.getApiKey(), taskType, issues);
   }
}
