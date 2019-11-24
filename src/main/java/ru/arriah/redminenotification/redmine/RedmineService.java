package ru.arriah.redminenotification.redmine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import ru.arriah.redminenotification.redmine.entity.Issue;
import ru.arriah.redminenotification.redmine.response.IssueListResponse;
import ru.arriah.redminenotification.util.HttpEntityFactory;
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
      return executor.<IssueListResponse>builder()
            .forUrl(urlBuilder -> urlBuilder.issues().json().assignedTo(id))
            .forMethod(HttpMethod.GET)
            .forEntity(HttpEntityFactory::getHttpEntity)
            .forClass(IssueListResponse.class)
            .exchange()
            .getBody()
            .getIssues();
   }
}
