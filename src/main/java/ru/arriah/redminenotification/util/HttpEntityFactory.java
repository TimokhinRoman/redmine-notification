package ru.arriah.redminenotification.util;

import org.springframework.http.HttpEntity;

public interface HttpEntityFactory {
   <T> HttpEntity<T> getHttpEntity();

   <T> HttpEntity<T> getHttpEntity(T body);
}
