package ru.arriah.redminenotification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.arriah.redminenotification.redmine.Issue;
import ru.arriah.redminenotification.redmine.IssueList;
import ru.arriah.redminenotification.util.HttpEntityFactory;
import ru.arriah.redminenotification.util.RedmineUrlBuilder;

import java.util.List;

@Service
public class RedmineService {

   private final RestTemplate restTemplate;
   private final RedmineUrlBuilder urlBuilder;
   private final HttpEntityFactory requestFactory;

   @Autowired
   public RedmineService(RestTemplate restTemplate, RedmineUrlBuilder urlBuilder, HttpEntityFactory requestFactory) {
      this.restTemplate = restTemplate;
      this.urlBuilder = urlBuilder;
      this.requestFactory = requestFactory;
   }

   public List<Issue> getIssuesAssignedToMe() {
      return getIssuesAssignedTo("me");
   }

   public List<Issue> getIssuesAssignedTo(String id) {
      String url = urlBuilder.issues().json().assignedTo(id).toUrl();
      HttpEntity<Object> request = requestFactory.getHttpEntity();
      ResponseEntity<IssueList> response = restTemplate.exchange(url, HttpMethod.GET, request, IssueList.class);
      IssueList issueList = response.getBody();
      return issueList.getIssues();
   }
}
