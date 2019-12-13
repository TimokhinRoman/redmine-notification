package ru.arriah.redminenotification.redmine;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.arriah.redminenotification.redmine.entity.CustomField;
import ru.arriah.redminenotification.redmine.entity.Entity;
import ru.arriah.redminenotification.redmine.entity.Issue;
import ru.arriah.redminenotification.redmine.entity.NamedEntity;
import ru.arriah.redminenotification.util.MessageFormatter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.isEmpty;

@Slf4j
@Component
public class IssueViewGenerator {

   private static final int BUG = 1;
   private static final int FEATURE = 2;

   private static final int SEVERENESS_FIELD = 2;

   private final MessageFormatter formatter;
   private final String redmineUrl;

   public IssueViewGenerator(MessageFormatter formatter, @Value("${redmine.url}") String redmineUrl) {
      this.formatter = formatter;
      this.redmineUrl = redmineUrl;
   }

   public String getView(Issue issue) {
      Objects.requireNonNull(issue);

      int type = ofNullable(issue.getTracker())
            .map(Entity::getId)
            .orElse(-1);

      List<String> fields;
      switch (type) {
         case BUG:
            fields = getBugFields(issue);
            break;
         case FEATURE:
            fields = getFeatureFields(issue);
            break;
         default:
            throw new IllegalArgumentException("Unknown issue type");
      }

      return fields.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.joining("\n"));
   }

   private List<String> getBugFields(Issue issue) {
      return Arrays.asList(
            formatter.header(issue.getSubject()),
            getStatus(issue),
            getPriority(issue),
            getAssignedTo(issue),
            getVersion(issue),
            getCustomFields(issue, SEVERENESS_FIELD),
            getUrl(issue)
      );
   }

   private List<String> getFeatureFields(Issue issue) {
      return Arrays.asList(
            formatter.header(issue.getSubject()),
            getStatus(issue),
            getPriority(issue),
            getAssignedTo(issue),
            getVersion(issue),
            getUrl(issue)
      );
   }

   private String getStatus(Issue issue) {
      return formatter.bold("Статус: ") + getValue(issue.getStatus());
   }

   private String getPriority(Issue issue) {
      return formatter.bold("Приоритет: ") + getValue(issue.getPriority());
   }

   private String getAssignedTo(Issue issue) {
      return formatter.bold("Назначена: ") + getValue(issue.getAssignedTo());
   }

   private String getVersion(Issue issue) {
      return formatter.bold("Версия: ") + getValue(issue.getVersion());
   }

   private String getCustomFields(Issue issue, int id) {
      CustomField field = issue.getCustomFields().get(id);
      if (field == null) return null;
      return formatter.bold(field.getName() + ": ") + getValue(field.getValue());
   }

   private String getUrl(Issue issue) {
      return String.format("%s/issues/%s", redmineUrl, issue.getId());
   }

   private String getValue(@Nullable NamedEntity entity) {
      return getValue(ofNullable(entity).map(NamedEntity::getName).orElse(null));
   }

   private String getValue(String str) {
      return str != null && !isEmpty(str) ? str : "-";
   }
}
