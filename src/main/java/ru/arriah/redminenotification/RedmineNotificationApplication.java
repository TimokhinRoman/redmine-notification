package ru.arriah.redminenotification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.arriah.redminenotification.redmine.Issue;
import ru.arriah.redminenotification.service.RedmineService;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@EnableScheduling
public class RedmineNotificationApplication implements CommandLineRunner {

   @Autowired
   private RedmineService redmineService;

   public static void main(String[] args) {
      SpringApplication.run(RedmineNotificationApplication.class, args);
   }

   @Override
   public void run(String... args) {
      String command;
      while (true) {
         Scanner scanner = new Scanner(System.in);
         System.out.println("Enter command: ");
         command = scanner.next();
         switch (command) {
            case "issues":
               printIssues();
               break;
            case "q":
               System.exit(-1);
         }
      }
   }

   private void printIssues() {
      List<Issue> issues = redmineService.getIssuesAssignedToMe();
      issues.forEach(System.out::println);
   }
}
