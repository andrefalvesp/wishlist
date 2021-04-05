package com.andrefalvesp.wishlist.gateways;

import com.andrefalvesp.wishlist.domains.WishListItem;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends MongoRepository<WishListItem, String> {

  List<WishListItem> findByCustomerIdAndStoreId(String customerId, String storeId);

  WishListItem findByCustomerIdAndStoreIdAndProductId(String customerId, String storeId,
      String productId);

  Long deleteByCustomerIdAndStoreIdAndProductId(String customerId, String storeId,
      String productId);

}