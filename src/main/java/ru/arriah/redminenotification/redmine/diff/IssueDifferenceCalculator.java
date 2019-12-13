package ru.arriah.redminenotification.redmine.diff;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.arriah.redminenotification.redmine.entity.Issue;
import ru.arriah.redminenotification.util.CollectionUtils;

import java.util.*;
import java.util.function.BiConsumer;

import static java.util.Objects.isNull;

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

      for (IssuePair pair : map.values()) {
         IssueDifference diff = diff(pair.getOldIssue(), pair.getNewIssue());
         if (diff != null) {
            result.add(diff);
         }
      }

      return result;
   }

   @Nullable
   private static IssueDifference diff(Issue oldIssue, Issue newIssue) {
      boolean diff = false;

      if (isNull(oldIssue) != isNull(newIssue)) {
         diff = true;
      }

      return diff ? new IssueDifference(oldIssue, newIssue) : null;
   }

   private static void collectToPairMap(List<Issue> issues, Map<Integer, IssuePair> map, BiConsumer<IssuePair, Issue> setter) {
      for (Issue issue : issues) {
         IssuePair pair = CollectionUtils.computeIfAbsent(map, issue.getId(), IssuePair::new);
         setter.accept(pair, issue);
      }
   }
}
