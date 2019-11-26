package ru.arriah.redminenotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RedmineNotificationApplication {

   public static void main(String[] args) {
      SpringApplication.run(RedmineNotificationApplication.class, args);
   }
}
