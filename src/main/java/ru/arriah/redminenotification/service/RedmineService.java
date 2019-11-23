package ru.arriah.redminenotification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import ru.arriah.redminenotification.redmine.Issue;
import ru.arriah.redminenotification.redmine.IssueList;
import ru.arriah.redminenotification.util.HttpEntityFactory;
import ru.arriah.redminenotification.util.RedmineUrlBuilder;
import ru.arriah.redminenotification.util.RequestBuilderExecutor;

import java.util.List;

@Service
public class RedmineService {

   private final RequestBuilderExecutor<RedmineUrlBuilder> executor;

   @Autowired
   public RedmineService(RequestBuilderExecutor<RedmineUrlBuilder> executor) {
      this.executor = executor;
   }

   public List<Issue> getIssuesAssignedToMe() {
      return getIssuesAssignedTo("me");
   }

   public List<Issue> getIssuesAssignedTo(String id) {
      return executor.<IssueList>builder()
            .forUrl(urlBuilder -> urlBuilder.issues().json().assignedTo(id))
            .forMethod(HttpMethod.GET)
            .forEntity(HttpEntityFactory::getHttpEntity)
            .forClass(IssueList.class)
            .exchange()
            .getBody()
            .getIssues();
   }
}
