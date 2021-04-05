package com.andrefalvesp.wishlist.gateways;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.andrefalvesp.wishlist.domains.WishListItemBuilder;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.mockito.Mockito;

public class WishListGatewayImplTest {

  private final WishListRepository wishListRepository = Mockito.mock(WishListRepository.class);
  private final WishListGatewayImpl wishListGateway = new WishListGatewayImpl(wishListRepository);

  @Test
  public void given_ValidParans_When_FindByCustomerIdAndStoreId_Then_Success() {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";

    final var expected =
        Stream.of(
            WishListItemBuilder.ITEM_DEFAULT_1(),
            WishListItemBuilder.ITEM_DEFAULT_2(),
            WishListItemBuilder.ITEM_DEFAULT_3()
        ).collect(Collectors.toList());

    when(wishListRepository.findByCustomerIdAndStoreId(customerId, storeId)).thenReturn(expected);

    final var actual = wishListGateway.findByCustomerIdAndStoreId(customerId, storeId);

    assertThat(actual).isEqualTo(expected);

    assertThat(actual).usingRecursiveComparison().isEqualTo( Stream.of(
        WishListItemBuilder.ITEM_DEFAULT_1(),
        WishListItemBuilder.ITEM_DEFAULT_2(),
        WishListItemBuilder.ITEM_DEFAULT_3()
    ).collect(Collectors.toList()));

    verify(wishListRepository, times(1)).findByCustomerIdAndStoreId(customerId, storeId);
  }

  @Test
  public void given_ValidParans_When_FindByCustomerIdAndStoreIdAndProductId_Then_Success() {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";
    final var productId = "8888888888";

    final var expected =
        WishListItemBuilder.ITEM_DEFAULT_3();

    when(wishListRepository.findByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId))
        .thenReturn(
            expected);

    final var actual = wishListGateway
        .findByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId);

    assertThat(actual).isEqualTo(expected);

    assertThat(actual).usingRecursiveComparison().isEqualTo(
        WishListItemBuilder.ITEM_DEFAULT_3());

    verify(wishListRepository, times(1))
        .findByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId);

  }

  @Test
  public void given_ValidParans_When_DeleteByCustomerIdAndStoreIdAndProductId_Then_Success() {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";
    final var productId = "8888888888";

    final long DELETE_ITEM_FOUND = 1L;

    when(
        wishListRepository.deleteByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId))
        .thenReturn(DELETE_ITEM_FOUND);

    final var actual = wishListGateway
        .deleteByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId);

    assertThat(actual).isEqualTo(DELETE_ITEM_FOUND);

    verify(wishListRepository, times(1))
        .deleteByCustomerIdAndStoreIdAndProductId(customerId, storeId, productId);
  }

  @Test
  public void given_ValidParans_When_InsertItem_Then_Success() {

    final var expected =
        WishListItemBuilder.ITEM_DEFAULT_3();

    when(wishListRepository.insert(expected)).thenReturn(expected);

    final var actual = wishListGateway
        .insertItem(expected);

    assertThat(actual).isEqualTo(expected);

    assertThat(actual).usingRecursiveComparison().isEqualTo(
        WishListItemBuilder.ITEM_DEFAULT_3());

    verify(wishListRepository, times(1))
        .insert(expected);
  }
}