package ru.arriah.redminenotification.redmine;

import ru.arriah.redminenotification.redmine.entity.Issue;
import ru.arriah.redminenotification.telegram.TelegramService;
import ru.arriah.redminenotification.telegram.entity.Message;

public interface TelegramIssueService extends TelegramService {
   Message sendIssue(String chatId, Issue issue);
}
