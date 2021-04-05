package com.andrefalvesp.wishlist.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DuplicateItemException extends RuntimeException {

  private final String customerId;
  private final String storeId;
  private final String productId;

  public String getMessage() {
    return "Duplicate item for this user " + customerId + " and store " + storeId + ".This product " + productId + " already exists.";
  }
}