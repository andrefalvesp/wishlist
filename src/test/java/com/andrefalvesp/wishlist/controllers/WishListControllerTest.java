package com.andrefalvesp.wishlist.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.andrefalvesp.wishlist.controllers.converters.WishListItemDomainToJson;
import com.andrefalvesp.wishlist.domains.WishListItemBuilder;
import com.andrefalvesp.wishlist.exception.DeleteItemNotFoundException;
import com.andrefalvesp.wishlist.exception.DuplicateItemException;
import com.andrefalvesp.wishlist.exception.ExceptionHandler;
import com.andrefalvesp.wishlist.exception.MaxItemsExceededException;
import com.andrefalvesp.wishlist.usecases.CrudWishList;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class WishListControllerTest {

  private static final String API_URL = "/wishlist/item";
  private static final String CUSTOMER_ID_PARAM = "customerId";
  private static final String STORE_ID_PARAM = "storeId";
  private static final String PRODUCT_ID_PARAM = "productId";

  private MockMvc mockMvc;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final CrudWishList crudWishList = Mockito.mock(CrudWishList.class);
  private final WishListItemDomainToJson domainToJson = new WishListItemDomainToJson(objectMapper);
  private final WishListController wishListController = new WishListController(crudWishList,
      domainToJson);

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(wishListController)
        .setControllerAdvice(new ExceptionHandler()).build();
  }

  @Test
  public void give_ValidParams_When_GetAllItems_Then_Success() throws Exception {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";

    final var itemList =
        Stream.of(
            WishListItemBuilder.ITEM_DEFAULT_1(),
            WishListItemBuilder.ITEM_DEFAULT_2(),
            WishListItemBuilder.ITEM_DEFAULT_3()
        ).collect(Collectors.toList());

    when(crudWishList.getAllItems(customerId, storeId)).thenReturn(itemList);

    final MvcResult mvcResult = mockMvc.perform(get(API_URL)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .header(CUSTOMER_ID_PARAM, customerId)
        .header(STORE_ID_PARAM, storeId)
    ).andReturn();

    final var actual = mvcResult.getResponse().getContentAsString();
    final var expected = objectMapper.writeValueAsString(itemList);

    assertThat(mvcResult.getResponse().getStatus(), is(HttpStatus.OK.value()));
    assertEquals(actual, expected);
    verify(crudWishList, times(1)).getAllItems(customerId, storeId);

  }

  @Test
  public void give_ValidParams_When_GetItem_Then_Success() throws Exception {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";
    final var productId = "8888888888";

    final var item =
        WishListItemBuilder.ITEM_DEFAULT_3();

    when(crudWishList.getItem(customerId, storeId, productId)).thenReturn(item);

    final MvcResult mvcResult = mockMvc.perform(get(API_URL + "/" + productId)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .header(CUSTOMER_ID_PARAM, customerId)
        .header(STORE_ID_PARAM, storeId)
    ).andReturn();

    final var actual = mvcResult.getResponse().getContentAsString();
    final var expected = objectMapper.writeValueAsString(item);

    assertThat(mvcResult.getResponse().getStatus(), is(HttpStatus.OK.value()));
    assertEquals(actual, expected);
    verify(crudWishList, times(1)).getItem(customerId, storeId, productId);

  }

  @Test
  public void give_ValidParams_When_AddItem_Then_Success() throws Exception {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";
    final var productId = "8888888888";

    final var item =
        WishListItemBuilder.ITEM_DEFAULT_3();

    when(crudWishList.addItem(customerId, storeId, productId)).thenReturn(item);

    final MvcResult mvcResult = mockMvc.perform(post(API_URL)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .header(CUSTOMER_ID_PARAM, customerId)
        .header(STORE_ID_PARAM, storeId)
        .header(PRODUCT_ID_PARAM, productId)

    ).andReturn();

    final var actual = mvcResult.getResponse().getContentAsString();
    final var expected = objectMapper.writeValueAsString(item);

    assertThat(mvcResult.getResponse().getStatus(), is(HttpStatus.CREATED.value()));
    assertEquals(actual, expected);
    verify(crudWishList, times(1)).addItem(customerId, storeId, productId);

  }

  @Test
  public void give_DuplicateProductException_When_AddItem_Then_Success() throws Exception {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";
    final var productId = "8888888888";

    when(crudWishList.addItem(customerId, storeId, productId))
        .thenThrow(new DuplicateItemException(customerId, storeId, productId));

    final MvcResult mvcResult = mockMvc.perform(post(API_URL)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .header(CUSTOMER_ID_PARAM, customerId)
        .header(STORE_ID_PARAM, storeId)
        .header(PRODUCT_ID_PARAM, productId)

    ).andReturn();

    assertThat(mvcResult.getResponse().getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    assertThrows(DuplicateItemException.class,
        () -> {
          throw new DuplicateItemException(customerId, storeId, productId);
        });
    verify(crudWishList, times(1)).addItem(customerId, storeId, productId);

  }

  @Test
  public void give_MaxItemsExceededException_When_AddItem_Then_Success() throws Exception {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";
    final var productId = "8888888888";

    when(crudWishList.addItem(customerId, storeId, productId))
        .thenThrow(new MaxItemsExceededException(customerId, storeId));

    final MvcResult mvcResult = mockMvc.perform(post(API_URL)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .header(CUSTOMER_ID_PARAM, customerId)
        .header(STORE_ID_PARAM, storeId)
        .header(PRODUCT_ID_PARAM, productId)

    ).andReturn();

    assertThat(mvcResult.getResponse().getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    assertThrows(MaxItemsExceededException.class,
        () -> {
          throw new MaxItemsExceededException(customerId, storeId);
        });
    verify(crudWishList, times(1)).addItem(customerId, storeId, productId);

  }

  @Test
  public void give_ValidParams_When_DeleteItem_Then_Success() throws Exception {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";
    final var productId = "8888888888";

    final MvcResult mvcResult = mockMvc.perform(delete(API_URL)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .header(CUSTOMER_ID_PARAM, customerId)
        .header(STORE_ID_PARAM, storeId)
        .header(PRODUCT_ID_PARAM, productId)

    ).andReturn();

    assertThat(mvcResult.getResponse().getStatus(), is(HttpStatus.OK.value()));
    verify(crudWishList, times(1)).deleteItem(customerId, storeId, productId);

  }

  @Test
  public void give_DeleteItemNotFoundException_When_DeleteItem_Then_Success() throws Exception {

    final var customerId = "1234567890";
    final var storeId = "L_NETSHOES";
    final var productId = "8888888888";

    doThrow(DeleteItemNotFoundException.class).when(crudWishList).deleteItem(customerId, storeId, productId);

    final MvcResult mvcResult = mockMvc.perform(delete(API_URL)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .header(CUSTOMER_ID_PARAM, customerId)
        .header(STORE_ID_PARAM, storeId)
        .header(PRODUCT_ID_PARAM, productId)

    ).andReturn();

    assertThat(mvcResult.getResponse().getStatus(), is(HttpStatus.NOT_FOUND.value()));
    assertThrows(DeleteItemNotFoundException.class,
        () -> {
          throw new DeleteItemNotFoundException(customerId, storeId, productId);
        });
    verify(crudWishList, times(1)).deleteItem(customerId, storeId, productId);

  }
}