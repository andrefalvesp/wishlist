package com.andrefalvesp.wishlist.domains;

public class WishListItemBuilder {

  public static WishListItem ITEM_DEFAULT_1() {
    return WishListItem
        .builder()
        .storeId("L_NETSHOES")
        .customerId("1234567890")
        .productId("0987654321")
        .build();
  }

  public static WishListItem ITEM_DEFAULT_2() {
    return WishListItem
        .builder()
        .storeId("L_NETSHOES")
        .customerId("1234567890")
        .productId("9999999999")
        .build();
  }

  public static WishListItem ITEM_DEFAULT_3() {
    return WishListItem
        .builder()
        .storeId("L_NETSHOES")
        .customerId("1234567890")
        .productId("8888888888")
        .build();
  }
}
