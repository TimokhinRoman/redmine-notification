package ru.arriah.redminenotification.redmine.diff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.arriah.redminenotification.redmine.entity.Issue;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public class IssueDifference {

   private final Issue oldIssue;
   private final Issue newIssue;

   public Issue getFirstNonNullIssue() {
      return Stream.of(newIssue, oldIssue)
            .filter(Objects::nonNull)
            .findFirst()
            .get();
   }
}
