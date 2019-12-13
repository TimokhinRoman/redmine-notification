package ru.arriah.redminenotification.redmine.task;

import org.springframework.stereotype.Component;
import ru.arriah.redminenotification.auth.UserToken;
import ru.arriah.redminenotification.redmine.entity.Issue;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedmineTaskIssuesHolder {

   private final Map<String, Map<Class, List<Issue>>> issues;

   public RedmineTaskIssuesHolder() {
      issues = new ConcurrentHashMap<>();
   }

   public <T extends AbstractRedmineTask> List<Issue> getIssues(UserToken userToken, Class<T> taskType) {
      return getIssuesForUser(userToken.getApiKey()).getOrDefault(taskType, Collections.emptyList());
   }

   public <T extends AbstractRedmineTask> void putIssues(UserToken userToken, Class<T> taskType, List<Issue> issues) {
      getIssuesForUser(userToken.getApiKey()).put(taskType, issues);
   }

   private Map<Class, List<Issue>> getIssuesForUser(String key) {
      return issues.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
   }
}
