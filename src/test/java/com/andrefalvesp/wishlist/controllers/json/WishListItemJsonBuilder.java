package com.andrefalvesp.wishlist.controllers.json;

public class WishListItemJsonBuilder {

  public static WishListItemJson ITEM_DEFAULT_1() {
    return WishListItemJson
        .builder()
        .storeId("L_NETSHOES")
        .customerId("1234567890")
        .productId("0987654321")
        .build();
  }

  public static WishListItemJson ITEM_DEFAULT_2() {
    return WishListItemJson
        .builder()
        .storeId("L_NETSHOES")
        .customerId("1234567890")
        .productId("9999999999")
        .build();
  }

  public static WishListItemJson ITEM_DEFAULT_3() {
    return WishListItemJson
        .builder()
        .storeId("L_NETSHOES")
        .customerId("1234567890")
        .productId("8888888888")
        .build();
  }
}
