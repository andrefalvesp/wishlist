package com.andrefalvesp.wishlist.gateways;

import com.andrefalvesp.wishlist.domains.WishListItem;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@AllArgsConstructor
public class WishListGatewayImpl implements WishListGateway {

  @Autowired
  private final WishListRepository wishListRepository;

  @Override
  public List<WishListItem> findByCustomerIdAndStoreId(final String customerId,final String storeId) {
    return wishListRepository.findByCustomerIdAndStoreId(customerId, storeId);
  }

  @Override
  public WishListItem findByCustomerIdAndStoreIdAndProductId(final String customerId, final String storeId,
      String productId) {
    return wishListRepository.findByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId);
  }

  @Override
  public Long deleteByCustomerIdAndStoreIdAndProductId(final String customerId, final String storeId,
      String productId) {
     return wishListRepository.deleteByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId);
  }

  @Override
  public WishListItem insertItem(final WishListItem item) {
    return wishListRepository.insert(item);
  }
}