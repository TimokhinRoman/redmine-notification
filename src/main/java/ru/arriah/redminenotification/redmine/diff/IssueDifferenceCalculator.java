package ru.arriah.redminenotification.redmine.diff;

import lombok.Getter;
import lombok.Setter;
import ru.arriah.redminenotification.redmine.entity.Issue;
import ru.arriah.redminenotification.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class IssueDifferenceCalculator {

   @Getter
   @Setter
   private static class IssuePair {
      private Issue oldIssue;
      private Issue newIssue;
   }

   public static List<IssueDifference> diff(List<Issue> oldIssues, List<Issue> newIssues) {
      Map<Integer, IssuePair> map = new HashMap<>();
      collectToPairMap(oldIssues, map, IssuePair::setOldIssue);
      collectToPairMap(newIssues, map, IssuePair::setNewIssue);

      List<IssueDifference> result = new ArrayList<>();
      for (Issue newIssue : newIssues) {

      }

      return result;
   }

   public static IssueDifference diff(Issue oldIssue, Issue newIssue) {

      return new IssueDifference();
   }

   private static void collectToPairMap(List<Issue> issues, Map<Integer, IssuePair> map, BiConsumer<IssuePair, Issue> setter) {
      for (Issue issue : issues) {
         IssuePair pair = CollectionUtils.computeIfAbsent(map, issue.getId(), IssuePair::new);
         setter.accept(pair, issue);
      }
   }
}
