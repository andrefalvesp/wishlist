package com.andrefalvesp.wishlist.gateways;

import com.andrefalvesp.wishlist.domains.WishListItem;
import java.util.List;

public interface WishListGateway {

  List<WishListItem> findByCustomerIdAndStoreId(String customerId, String storeId);

  WishListItem findByCustomerIdAndStoreIdAndProductId(String customerId, String storeId,
      String productId);

  Long deleteByCustomerIdAndStoreIdAndProductId(String customerId, String storeId,
      String productId);

  WishListItem insertItem(WishListItem item);

}
