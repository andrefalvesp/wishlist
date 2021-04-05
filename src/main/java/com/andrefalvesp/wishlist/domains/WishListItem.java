package com.andrefalvesp.wishlist.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Document(collection = "wishListItem")
@CompoundIndex(name = "customerProductStoreIndex", def = "{'customerId': 1, 'productId': 1, 'storeId': 1}", background = true)

public class WishListItem {

  @Id
  @JsonIgnore
  @NotBlank(message = "wishList.id.notBlank")
  private String id;
  @NotBlank(message = "wishList.customerId.notBlank")
  private String customerId;
  @NotBlank(message = "wishList.storeId.notBlank")
  private String storeId;
  @NotBlank(message = "wishList.productId.notBlank")
  private String productId;

}