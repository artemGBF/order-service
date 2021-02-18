package ru.gbf.orderservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gbf.orderservice.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
