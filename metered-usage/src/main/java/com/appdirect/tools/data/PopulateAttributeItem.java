package com.appdirect.tools.data;


import org.springframework.stereotype.Service;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PopulateAttributeItem {

  public Map<String, String> getAttributesMap() {
    return Collections.unmodifiableMap(Stream.of(
        new SimpleEntry<>("attribute1", "value1"),
        new SimpleEntry<>("attribute2", "value2"),
        new SimpleEntry<>("attribute3", "value3"))
        .collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));
  }
}

