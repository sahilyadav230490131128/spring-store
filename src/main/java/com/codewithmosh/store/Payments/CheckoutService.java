package com.codewithmosh.store.Payments;

import com.codewithmosh.store.Exceptions.CartEmptyException;
import com.codewithmosh.store.Exceptions.CartNotFoundException;
import com.codewithmosh.store.Service.AuthService;
import com.codewithmosh.store.Service.CartService;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;
    @Value("${websiteUrl}")
    private String websiteUrl;
    @Transactional
    public CheckoutResponse checkout(CheckoutRequest checkoutRequest){

      var cart = cartRepository.findById(checkoutRequest.getCartId()).orElse(null);
      if(cart==null)
      {
       throw new CartNotFoundException();
      }
      if(cart.isEmpty())
      {
        throw new CartEmptyException();
      }
      var order = Order.fromCart(cart,authService.getCurrentUser());
      orderRepository.save(order);
      try{
      var session = paymentGateway.createCheckoutSession(order);
      cartService.clearCart(cart.getId());
      return new CheckoutResponse(order.getId(), session.getCheckoutUrl());
      }
      catch (PaymentException ex)
      {
          orderRepository.delete(order);
          throw ex;
      }

  }
    public void handleWebhookEvents(WebhookRequest request) //(Map<String, String> signature, String payload)  now instead of passing here two parameters we can create a different class and pass the object of that class here.
    {
        /**
         * In this whole method there are two types of logics, some logics are specific to stripe like constructing the event,
         * deserializing the event data and extracting the order id, and the other logic is about updating the order status.
         * and this is not tied to a particular payment gateway so if tomorrow we want to shift to paypal payment gateway we still need these 3 lines for updating the order status.
         * so now we need to refactor this service and move all the logic inside our StripePaymentGateway class, but how do we do that , well if we see the purpose of the first few lines of
         * this method from constructing event to getting the order from it, the purpose of these lines is to take a WebhookRequest -> and return a new object that
         * have {order_id , paymentStatus} -> we can encapsulate these fields in the new class like (PaymentResult)  so lets make a class PaymentResult.
         */
        paymentGateway.
                parseWebhookRequest(request)
                .ifPresent(
                        paymentResult -> {
                          var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                            order.setStatus(paymentResult.getPaymentStatus());
                            orderRepository.save(order);
                        }
                );
    }
}
