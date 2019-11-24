package ru.arriah.redminenotification.redmine.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class NamedEntity extends Entity {

   protected String name;
}
