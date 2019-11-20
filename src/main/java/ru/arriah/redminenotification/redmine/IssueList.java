package ru.arriah.redminenotification.redmine;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IssueList {

   @JsonProperty("issues")
   private List<Issue> issues;
   @JsonProperty("limit")
   private int limit;
   @JsonProperty("offset")
   private int offset;
   @JsonProperty("total_count")
   private int totalCount;

}
