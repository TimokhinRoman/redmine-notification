package ru.arriah.redminenotification.util;

public interface UrlBuilderFactory<T extends UrlBuilder> {
   T newBuilder();
}
