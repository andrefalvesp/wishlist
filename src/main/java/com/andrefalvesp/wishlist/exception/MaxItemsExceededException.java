package com.andrefalvesp.wishlist.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MaxItemsExceededException extends RuntimeException {

  private final String customerId;
  private final String storeId;

  public String getMessage() {
    return "Max wish list items exceeded for this user " + customerId + " and store " +storeId+". Limit is 20 items.";
  }
}