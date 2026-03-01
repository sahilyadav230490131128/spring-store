package com.codewithmosh.store.Service;

import com.codewithmosh.store.Exceptions.CartNotFoundException;
import com.codewithmosh.store.Exceptions.ProductNotFoundException;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.Cart_ItemsDto;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.mappers.CartMapper;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
@AllArgsConstructor
@Service
public class CartService {

    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private ProductRepository productRepository;


    public CartDto createCart()
    {
        var cart = new Cart();
        cartRepository.save(cart);
        var cartDto = cartMapper.toDto(cart);
        return cartDto;
    }

    public Cart_ItemsDto addItemToCart(UUID cartId, Long productId,Integer quantity)
    {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart==null)
        {
            throw new CartNotFoundException();
        }
        var product = productRepository.findById(productId).orElse(null);
        if(product==null)
        {
            throw new ProductNotFoundException();
        }
        var cartItem = cart.addItems(product,quantity);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId)
    {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart==null)
        {
           throw new CartNotFoundException();
        }
        return cartMapper.toDto(cart);
    }

    public Cart_ItemsDto updateItemsInCart(UUID cartId,Long productId,Integer quantity)
    {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart==null)
        {
            throw new CartNotFoundException();
        }
        var cartItems = cart.getItems(productId);
        if(cartItems==null)
        {
            throw new ProductNotFoundException();
        }
        cartItems.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItems);
    }

    public void deleteItemFromCart(UUID cartId,Long productId)
    {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart==null)
        {
           throw new CartNotFoundException();
        }
        var cartItem = cart.getItems(productId);
        if(cartItem==null)
        {
            throw new ProductNotFoundException();
        }
        cart.removeItems(productId);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId)
    {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart==null)
        {
           throw new CartNotFoundException();
        }
        cart.removeAllItems();
        cartRepository.save(cart);
    }
}
