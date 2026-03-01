package com.codewithmosh.store.Payments;

import com.codewithmosh.store.entities.Order;

import java.util.Optional;


public interface PaymentGateway {

    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
    /**
     * now earlier we saw that when the user a user makes a payment,stripe sends us a few events out of these we are
     * interested in one event and that is Payment_intent Succeeded or failed so as part of parsing a webhook request we
     * can not always get a paymentResult so lets make the return type of this method as optional. so now our
     * payment gateway should have the capability to take our request and give us a paymentResult. Note that in this design there is nothing specific to stripe.
     * here in this method a WebhookRequest is an abstraction that contain HttpHeader and a payload so it has all the data we need for parsing a webhook request
     * and getting a paymentResult which is again another abstraction that is not tied to stripe or anyother payment gateway
     * now lets implement this new method in our StripePaymentGateway.
     *
     */
}
