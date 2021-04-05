package com.andrefalvesp.wishlist.controllers.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class WishListItemJson {

  private String customerId;
  private String storeId;
  private String productId;

}
