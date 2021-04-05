package com.andrefalvesp.wishlist.controllers;

import com.andrefalvesp.wishlist.controllers.converters.WishListItemDomainToJson;
import com.andrefalvesp.wishlist.controllers.json.WishListItemJson;
import com.andrefalvesp.wishlist.usecases.CrudWishList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist/item")
@RequiredArgsConstructor
public class WishListController {

  private static final String CUSTOMER_ID = "customerId";
  private static final String STORE_ID = "storeId";
  private static final String PRODUCT_ID = "productId";

  private final CrudWishList crudWishList;
  private final WishListItemDomainToJson domainToJson;

  @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<WishListItemJson> getAllItems(

      @RequestHeader(name = CUSTOMER_ID) final String customerId,
      @RequestHeader(name = STORE_ID) final String storeId
  ) {

    return crudWishList
            .getAllItems(customerId, storeId)
            .stream()
            .map(domainToJson::convert)
            .collect(Collectors.toList());
  }

  @GetMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public WishListItemJson getItem(
      @RequestHeader(name = CUSTOMER_ID) final String customerId,
      @RequestHeader(name = STORE_ID) final String storeId,
      @PathVariable(name = PRODUCT_ID) final String productId
  ) {

    return domainToJson.convert(crudWishList.getItem(customerId, storeId, productId));

  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public WishListItemJson addItem(
      @RequestHeader(name = CUSTOMER_ID) final String customerId,
  @RequestHeader(name = STORE_ID) final String storeId,
  @RequestHeader(name = PRODUCT_ID) final String productId
  ) {
    return domainToJson.convert(crudWishList.addItem(customerId,storeId, productId));
  }

  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void deleteItem(
      @RequestHeader(name = CUSTOMER_ID) final String customerId,
  @RequestHeader(name = STORE_ID) final String storeId,
  @RequestHeader(name = PRODUCT_ID) final String productId
  ) {
    crudWishList.deleteItem(customerId, storeId, productId);
  }

}