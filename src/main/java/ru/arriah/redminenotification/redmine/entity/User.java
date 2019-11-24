package ru.arriah.redminenotification.redmine.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true, exclude = {"apiKey"})
public class User extends NamedEntity {

   private String apiKey;
}
