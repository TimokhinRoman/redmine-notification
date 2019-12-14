package ru.arriah.redminenotification.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CollectionUtils {

   public static <K, V> V computeIfAbsent(@NotNull Map<K, V> map, @Nullable K key, @NotNull Supplier<V> supplier) {
      return map.computeIfAbsent(key, k -> supplier.get());
   }

   @NotNull
   public static <K, V> Map<K, V> toMap(@Nullable Collection<V> col, @NotNull Function<V, K> keyMapper) {
      if (col == null) return Collections.emptyMap();
      return col.stream()
            .collect(Collectors.toMap(keyMapper, Function.identity()));
   }
}
