package com.farmconnect.farmconnect_backend.repository;

import com.farmconnect.farmconnect_backend.model.Order;
import com.farmconnect.farmconnect_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyer(User buyer);
    List<Order> findByFarmerId(Long farmerId);
}
