package ru.arriah.redminenotification.redmine.task;

interface Schedulable extends Runnable {
   long getDelay();
}
