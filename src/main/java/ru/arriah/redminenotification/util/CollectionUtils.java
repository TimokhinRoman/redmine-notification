package ru.arriah.redminenotification.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public class CollectionUtils {

   public static <K, V> V computeIfAbsent(@NotNull Map<K, V> map, @Nullable K key, @NotNull Supplier<V> supplier) {
      return map.computeIfAbsent(key, k -> supplier.get());
   }
}
