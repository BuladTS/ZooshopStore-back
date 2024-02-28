package ru.sfu.zooshopback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshopback.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
