package com.codewithmosh.store.Controllers;

import com.codewithmosh.store.Exceptions.CartNotFoundException;
import com.codewithmosh.store.Exceptions.ProductNotFoundException;
import com.codewithmosh.store.Service.CartService;
import com.codewithmosh.store.dtos.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/Cart")
public class CartController {

    private CartService cartService;


    @PostMapping
  public ResponseEntity<CartDto> createCart(
          UriComponentsBuilder builder
    ) {
        var cartDto = cartService.createCart();
        var uri = builder.path("/Cart/{id}").buildAndExpand(cartDto.getId()).toUri();
        return  ResponseEntity.created(uri).body(cartDto);
  }
  @PostMapping("/{cartId}/items")
  public ResponseEntity<Cart_ItemsDto> addingProductToCart(
          @PathVariable(name = "cartId") UUID cartId,
         @Valid @RequestBody AddItemToCartRequest request
  )
  {
      var cartItemDto = cartService.addItemToCart(cartId, request.getProductId(),request.getQuantity());
      return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
  }

  @GetMapping("/{cartId}")
    public CartDto getCart(@PathVariable(name = "cartId") UUID cartId)
  {
      var cartDto = cartService.getCart(cartId);
      return cartDto;
  }

  @PutMapping("/{cartId}/items/{productId}")
    public Cart_ItemsDto updateCartItems(
            @PathVariable(name = "cartId") UUID cartId,
            @PathVariable(name = "productId") Long productId,
           @Valid @RequestBody updateCartRequest updateCartRequest
  )
  {
     var cartUpdateDto = cartService.updateItemsInCart(cartId,productId,updateCartRequest.getQuantity());
     return cartUpdateDto;
  }

  @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId)
  {
      cartService.deleteItemFromCart(cartId,productId);
      return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCart(
            @PathVariable(name = "cartId") UUID cartId
  )
  {
    cartService.clearCart(cartId);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleCartNotFound()
  {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
              Map.of("error","Cart Not Found")
      );
  }

  @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleProductNotFound()
  {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
              Map.of("error","product not found in the cart")
      );
  }
}
