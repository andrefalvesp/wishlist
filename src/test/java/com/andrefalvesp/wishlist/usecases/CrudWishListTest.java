package com.andrefalvesp.wishlist.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.andrefalvesp.wishlist.domains.WishListItem;
import com.andrefalvesp.wishlist.domains.WishListItemBuilder;
import com.andrefalvesp.wishlist.exception.DeleteItemNotFoundException;
import com.andrefalvesp.wishlist.exception.DuplicateItemException;
import com.andrefalvesp.wishlist.exception.MaxItemsExceededException;
import com.andrefalvesp.wishlist.gateways.WishListGateway;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.mockito.Mockito;

public class CrudWishListTest {

  private final WishListGateway wishListGateway = Mockito.mock(WishListGateway.class);
  private final CrudWishList crudList = new CrudWishList(wishListGateway);

  @Test
  public void given_ValidParams_When_GetAllItems_Then_Success() {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";

    final var expected =
        Stream.of(
            WishListItemBuilder.ITEM_DEFAULT_1(),
            WishListItemBuilder.ITEM_DEFAULT_2(),
            WishListItemBuilder.ITEM_DEFAULT_3()
        ).collect(Collectors.toList());

    when(wishListGateway.findByCustomerIdAndStoreId(customerId, storeId)).thenReturn(expected);

    final var actual = crudList.getAllItems(customerId, storeId);

    assertThat(actual).isEqualTo(expected);

    assertThat(actual).usingRecursiveComparison().isEqualTo(Stream.of(
        WishListItemBuilder.ITEM_DEFAULT_1(),
        WishListItemBuilder.ITEM_DEFAULT_2(),
        WishListItemBuilder.ITEM_DEFAULT_3()
    ).collect(Collectors.toList()));

    verify(wishListGateway, times(1)).findByCustomerIdAndStoreId(customerId, storeId);
  }

  @Test
  public void given_ValidParams_When_GetItem_Then_Success() {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";
    final var productId = "8888888888";

    final var expected =
        WishListItemBuilder.ITEM_DEFAULT_3();

    when(wishListGateway.findByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId))
        .thenReturn(expected);

    final var actual = crudList.getItem(customerId, storeId, productId);

    assertThat(actual).isEqualTo(expected);

    assertThat(actual).usingRecursiveComparison().isEqualTo(
        WishListItemBuilder.ITEM_DEFAULT_3());

    verify(wishListGateway, times(1))
        .findByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId);

  }

  @Test
  public void given_ValidParams_When_DeleteByCustomerIdAndStoreIdAndProductId_Then_Success() {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";
    final var productId = "8888888888";

    final long DELETE_ITEM_FOUND = 1L;

    when(wishListGateway.deleteByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId))
        .thenReturn(DELETE_ITEM_FOUND);

    crudList.deleteItem(customerId, storeId, productId);

    verify(wishListGateway, times(1))
        .deleteByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId);
  }

  @Test(expected = DeleteItemNotFoundException.class)
  public void given_InvalidParams_When_DeleteByCustomerIdAndStoreIdAndProductId_Then_DeleteItemNotFoundException() {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";
    final var productId = "8888888888";

    final long DELETE_ITEM_NOT_FOUND = 0L;

    when(wishListGateway.deleteByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId))
        .thenReturn(DELETE_ITEM_NOT_FOUND);

    crudList.deleteItem(customerId, storeId, productId);

    verify(wishListGateway, times(1))
        .deleteByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId);
  }

  @Test
  public void given_ValidParams_When_InsertItem_Then_Success() {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";
    final var productId = "8888888888";

    final var expectedItem =
        WishListItemBuilder.ITEM_DEFAULT_3();

    final var expectedList =
        Stream.of(
            WishListItemBuilder.ITEM_DEFAULT_1(),
            WishListItemBuilder.ITEM_DEFAULT_2()
        ).collect(Collectors.toList());

    when(wishListGateway.insertItem(any())).thenReturn(expectedItem);
    when(wishListGateway.findByCustomerIdAndStoreId(customerId, storeId)).thenReturn(expectedList);

    final var actual = crudList.addItem(customerId, storeId, productId);

    assertThat(actual).isEqualTo(expectedItem);

    assertThat(actual).usingRecursiveComparison().isEqualTo(
        WishListItemBuilder.ITEM_DEFAULT_3());

    verify(wishListGateway, times(1)).insertItem(any());
  }

  @Test(expected = DuplicateItemException.class)
  public void given_InvalidParams_When_InsertItem_Then_DuplicateItemException() {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";
    final var productId = "8888888888";

    final var listItems =
        Stream.of(
            WishListItemBuilder.ITEM_DEFAULT_1(),
            WishListItemBuilder.ITEM_DEFAULT_2(),
            WishListItemBuilder.ITEM_DEFAULT_3()
        ).collect(Collectors.toList());

    when(wishListGateway.findByCustomerIdAndStoreId(customerId, storeId)).thenReturn(listItems);

    crudList.addItem(customerId, storeId, productId);

    verify(wishListGateway, times(0)).insertItem(any());

  }

  @Test(expected = MaxItemsExceededException.class)
  public void given_InvalidParams_When_InsertItem_Then_MaxItemsExceeededException() {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";
    final var productId = "8888888888";

    final int MAX_ITEMS_LIMIT = 20;

    final List<WishListItem> listItems = Collections.nCopies(MAX_ITEMS_LIMIT, WishListItemBuilder.ITEM_DEFAULT_1());

    when(wishListGateway.findByCustomerIdAndStoreId(customerId, storeId)).thenReturn(listItems);

    crudList.addItem(customerId, storeId, productId);

    verify(wishListGateway, times(0)).insertItem(any());

  }

}