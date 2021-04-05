package com.andrefalvesp.wishlist.controllers.converters;

import com.andrefalvesp.wishlist.controllers.json.WishListItemJson;
import com.andrefalvesp.wishlist.domains.WishListItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WishListItemDomainToJson implements Converter<WishListItem, WishListItemJson> {

  private final ObjectMapper objectMapper;

  @Override
  public WishListItemJson convert(final WishListItem item) {
    return objectMapper.convertValue(item, WishListItemJson.class);
  }

}