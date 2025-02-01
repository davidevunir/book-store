package com.bs.orders.utils;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class SearchStatement {

  String key;
  Object value;
  SearchOperation operation;
}
