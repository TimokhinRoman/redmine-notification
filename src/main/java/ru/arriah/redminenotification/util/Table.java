package ru.arriah.redminenotification.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class Table<K1, K2, V> {

   private final Map<K1, Map<K2, V>> map;
   private final Supplier<Map<K2, V>> subMapSupplier;

   public Table() {
      this(HashMap::new, HashMap::new);
   }

   public Table(Supplier<Map<K1, Map<K2, V>>> mapSupplier, Supplier<Map<K2, V>> subMapSupplier) {
      this.map = mapSupplier.get();
      this.subMapSupplier = subMapSupplier;
   }

   public V get(K1 key1, K2 key2) {
      Map<K2, V> subMap = map.get(key1);
      if (subMap == null) return null;
      return subMap.get(key2);
   }

   public V getOrDefault(K1 key1, K2 key2, V value) {
      return Optional.ofNullable(get(key1, key2)).orElse(value);
   }

   public V put(K1 key1, K2 key2, V value) {
      Map<K2, V> subMap = CollectionUtils.computeIfAbsent(map, key1, subMapSupplier);
      return subMap.put(key2, value);
   }

   public boolean containsKey(K1 key) {
      return map.containsKey(key);
   }

   public boolean containsKey(K1 key1, K2 key2) {
      Map<K2, V> subMap = map.get(key1);
      if (subMap == null) return false;
      return subMap.containsKey(key2);
   }
}
