package com.codewithmosh.store.Payments;

import com.codewithmosh.store.Exceptions.CartEmptyException;
import com.codewithmosh.store.Exceptions.CartNotFoundException;
import com.codewithmosh.store.dtos.ErrorDto;
import com.codewithmosh.store.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;


    private final OrderRepository orderRepository;

    @PostMapping
    public CheckoutResponse checkout(@RequestBody CheckoutRequest checkoutRequest)
    {
            return checkoutService.checkout(checkoutRequest);
    }

    @PostMapping("/webhook")
        public void webhook(
             //@RequestHeader("Stripe-signature") String signature // here due to this line our controller is tightly coupled to the stripe payment because due to the @RequestHeader("Stripe-signature") Stripe signature in the header so due to this it is tightly coupled to stripe payment so we need to make it more general therefore.
             @RequestHeader Map<String,String> header , //now our controller knows absolutely nothing about stripe.
             @RequestBody String payload
        )
    {
        System.out.println("webhook entered");
       checkoutService.handleWebhookEvents(new WebhookRequest(header,payload));
    }
    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException(Exception ex)
    {
        System.out.println(ex.getMessage());;
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error creating checkout session"));
    }
    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex)
    {
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
