package com.codewithmosh.store.Controllers;

import com.codewithmosh.store.Exceptions.OrderNotFoundException;
import com.codewithmosh.store.Service.OrderService;
import com.codewithmosh.store.dtos.ErrorDto;
import com.codewithmosh.store.dtos.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/Orders")
public class OrderController {

   private final OrderService orderService;
    @GetMapping
    public List<OrderDto> getAllOrders()
    {
       return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrder(
            @PathVariable("orderId") Long orderId
    )
    {
        return orderService.getOrder(orderId);
    }
     @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Void> handleOrderNotFoundException(Exception e)
     {
         return ResponseEntity.notFound().build();
     }

     @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(Exception e)
     {
         return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(e.getMessage()));
     }
}
