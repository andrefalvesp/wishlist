package com.andrefalvesp.wishlist.controllers.converters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.andrefalvesp.wishlist.controllers.json.WishListItemJsonBuilder;
import com.andrefalvesp.wishlist.domains.WishListItemBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class WishListItemDomainToJsonTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final WishListItemDomainToJson domainToJson = new WishListItemDomainToJson(objectMapper);

  @Test
  public void given_ValidParans_When_GetOrderList_Then_Success() {

    final var expected =
        WishListItemJsonBuilder.ITEM_DEFAULT_3();

    final var actual = domainToJson.convert(WishListItemBuilder.ITEM_DEFAULT_3());

    assertNotNull(actual);
    assertThat(actual.getCustomerId(), is(expected.getCustomerId()));
    assertThat(actual.getStoreId(), is(expected.getStoreId()));
    assertThat(actual.getProductId(), is(expected.getProductId()));

  }
}