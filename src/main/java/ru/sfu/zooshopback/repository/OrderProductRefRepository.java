package ru.sfu.zooshopback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.zooshopback.model.OrderProductRef;

@Repository
public interface OrderProductRefRepository extends JpaRepository<OrderProductRef, Long> {
}
