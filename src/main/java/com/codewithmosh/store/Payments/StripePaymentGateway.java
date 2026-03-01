package com.codewithmosh.store.Payments;

import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.Order_Items;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StripePaymentGateway implements PaymentGateway {

    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;
    @Value("${websiteUrl}")
    private String websiteUrl;

    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try {
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout_success/orderId=?" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout_cancel")
                    .putMetadata("order_id",order.getId().toString());

            order.getItems().forEach(item ->
                    {
                        var lineItem = getLineItem(order, item);
                        builder.addLineItem(lineItem);
                    }
            );
            var session = Session.create(builder.build());
            return new CheckoutSession(session.getUrl());
        }
        catch(StripeException ex)
        {
            System.out.println(ex.getMessage());
            throw new PaymentException();
        }
    }

    @Override
    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {

        try {
            var payload = request.getPayLoad();
            var signature = request.getHeaders().get("stripe-signature");
            var event = Webhook.constructEvent(payload,signature,webhookSecretKey);
            System.out.println("event created");
           return switch(event.getType()) {
               case "payment_intent.succeeded" ->
                       Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.PAID));

               case "payment_intent.payment_failed" ->
                       Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.FAILED));

               default -> Optional.empty();
           };
        }
        catch (SignatureVerificationException e)
        {
            throw new PaymentException("Invalid signature");
        }
    }

    public long extractOrderId(Event event)
    {
        var stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(
                ()->new PaymentException("Could not deserialize Stripe event. Check the SDK and API version")
                );
        var paymentIntent = (PaymentIntent) stripeObject;
        return Long.valueOf(paymentIntent.getMetadata().get("order_id"));
    }
    private SessionCreateParams.LineItem getLineItem(Order order, Order_Items item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(getPriceData(order, item))
                .build();
    }

    private  SessionCreateParams.LineItem.PriceData getPriceData(Order order, Order_Items item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(item.getUnit_price().multiply(BigDecimal.valueOf(100)))
                .setProductData(getProductData(order))
                .build();
    }

    private  SessionCreateParams.LineItem.PriceData.ProductData getProductData(Order order) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(order.getCustomer().getName())
                .build();
    }
}
