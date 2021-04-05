package com.andrefalvesp.wishlist.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteItemNotFoundException extends RuntimeException {

  private final String customerId;
  private final String storeId;
  private final String productId;

  public String getMessage() {
    return "Delete item not found for this user " + customerId + ", store " +storeId+" and productId " +productId;
  }
}