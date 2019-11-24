package ru.arriah.redminenotification.redmine.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.arriah.redminenotification.redmine.entity.Issue;

import java.util.List;

@Getter
@Setter
public class IssueListResponse {

   @JsonProperty("issues")
   private List<Issue> issues;
   @JsonProperty("limit")
   private int limit;
   @JsonProperty("offset")
   private int offset;
   @JsonProperty("total_count")
   private int totalCount;

}
