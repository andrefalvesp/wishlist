package com.andrefalvesp.wishlist.usecases;

import com.andrefalvesp.wishlist.domains.WishListItem;
import com.andrefalvesp.wishlist.exception.DeleteItemNotFoundException;
import com.andrefalvesp.wishlist.exception.DuplicateItemException;
import com.andrefalvesp.wishlist.exception.MaxItemsExceededException;
import com.andrefalvesp.wishlist.gateways.WishListGateway;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CrudWishList {

  private final WishListGateway wishListGateway;
  private final int MAX_ITEMS_LIMIT = 20;
  private final long DELETE_ITEM_FOUND = 1L;

  public List<WishListItem> getAllItems(final String customerId, final String storeId) {
    return wishListGateway.findByCustomerIdAndStoreId(customerId, storeId);
  }

  public WishListItem getItem(final String customerId, final String storeId,
      final String productId) {
    return wishListGateway.findByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId);
  }

  public WishListItem addItem(final String customerId, final String storeId,
      final String productId) throws MaxItemsExceededException, DuplicateItemException{

    validInsertItem(customerId, storeId, productId);
    return wishListGateway.insertItem(buildItem(customerId, storeId, productId));

  }

  public void deleteItem(final String customerId, final String storeId,
      final String productId) throws DeleteItemNotFoundException  {

    Stream
        .of(wishListGateway
            .deleteByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId))
        .filter(aLong -> aLong.equals(DELETE_ITEM_FOUND))
        .findAny()
        .orElseThrow(() -> new DeleteItemNotFoundException(customerId, storeId, productId));
  }

  private void validInsertItem(final String customerId, final String storeId,
      final String productId) {
    final List<WishListItem> itemList = getAllItems(customerId, storeId);

    validDuplicateItemProduct(customerId, storeId, productId, itemList);
    validMaxItems (customerId, storeId, itemList.size());

  }

  private void validDuplicateItemProduct(final String customerId, final String storeId, final String productId,
      final List<WishListItem> itemList) {
    itemList
        .stream()
        .filter(wishListItem -> wishListItem.getProductId().equalsIgnoreCase(productId))
        .findAny()
        .ifPresent(wishListItem -> {
          throw new DuplicateItemException(customerId, storeId, productId);
        });
  }

  private void validMaxItems(final String customerId, final String storeId, final int listSize) {
    Stream
        .of(listSize)
        .filter(aInt -> aInt < MAX_ITEMS_LIMIT)
        .findAny()
        .orElseThrow(() -> new MaxItemsExceededException(customerId, storeId));
  }

  private WishListItem buildItem(final String customerId, final String storeId,
      final String productId) {
    return WishListItem
        .builder()
        .customerId(customerId)
        .storeId(storeId)
        .productId(productId)
        .build();
  }
}