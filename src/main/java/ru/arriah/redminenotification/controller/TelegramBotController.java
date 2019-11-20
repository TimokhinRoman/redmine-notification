package ru.arriah.redminenotification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arriah.redminenotification.redmine.Issue;
import ru.arriah.redminenotification.service.RedmineService;
import ru.arriah.redminenotification.service.TelegramService;
import ru.arriah.redminenotification.telegram.Message;
import ru.arriah.redminenotification.telegram.request.MessageRequest;

import java.util.List;

@RestController
public class TelegramBotController {

   private final RedmineService redmine;
   private final TelegramService telegram;

   @Autowired
   public TelegramBotController(RedmineService redmine, TelegramService telegram) {
      this.redmine = redmine;
      this.telegram = telegram;
   }

   @GetMapping("issues")
   public List<Issue> issues() {
      return redmine.getIssuesAssignedToMe();
   }

   @PostMapping("sendMessage")
   public Message sendMessage(@RequestBody MessageRequest request) {
      return telegram.sendMessage(request);
   }
}
