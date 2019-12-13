package ru.arriah.redminenotification.util;

public class HtmlMessageFormatter implements MessageFormatter {

   @Override
   public String header(String text) {
      return "<b>" + text + "</b>";
   }

   @Override
   public String bold(String text) {
      return "<b>" + text + "</b>";
   }
}
