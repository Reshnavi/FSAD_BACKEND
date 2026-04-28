package com.farmconnect.farmconnect_backend.service;

import com.farmconnect.farmconnect_backend.dto.OrderRequest;
import com.farmconnect.farmconnect_backend.model.*;
import com.farmconnect.farmconnect_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Order createOrder(OrderRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User buyer = userRepository.findByEmail(email).orElseThrow();

        Order order = Order.builder()
                .buyer(buyer)
                .total(request.getTotal())
                .status("pending")
                .date(LocalDate.now())
                .farmerId(1L) // default; extend for multi-farmer if needed
                .build();

        List<OrderItem> items = request.getItems().stream().map(itemReq -> {
            Product product = productRepository.findById(itemReq.getProductId()).orElseThrow();
            return OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemReq.getQuantity())
                    .price(itemReq.getPrice())
                    .build();
        }).toList();

        order.setItems(items);
        return orderRepository.save(order);
    }

    public List<Order> getMyOrders() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User buyer = userRepository.findByEmail(email).orElseThrow();
        return orderRepository.findByBuyer(buyer);
    }

    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public Order updateStatus(Long id, Map<String, String> body) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus(body.get("status"));
        return orderRepository.save(order);
    }
}
