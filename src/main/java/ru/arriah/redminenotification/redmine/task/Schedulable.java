package ru.arriah.redminenotification.redmine.task;

interface Schedulable extends Runnable {
   int getDelay();
}
