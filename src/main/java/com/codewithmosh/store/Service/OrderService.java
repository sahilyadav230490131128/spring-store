package com.codewithmosh.store.Service;

import com.codewithmosh.store.Exceptions.OrderNotFoundException;
import com.codewithmosh.store.dtos.OrderDto;
import com.codewithmosh.store.mappers.OrderMapper;
import com.codewithmosh.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class OrderService {

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders()
    {
        var user = authService.getCurrentUser();
        var orders = orderRepository.getAllByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(Long orderId) {
        var order = orderRepository.getOrderWithItem(orderId).orElseThrow(OrderNotFoundException::new);
        var user = authService.getCurrentUser();
        if(!order.isPlacedBy(user))            //!order.getCustomer().getId().equals(user.getId())  information expert principle violation.
        {
            throw new AccessDeniedException("you don't have access to this order");
        }
        return orderMapper.toDto(order);
    }
}
