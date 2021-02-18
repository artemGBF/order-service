package ru.gbf.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gbf.orderservice.dto.OrderDTO;
import ru.gbf.orderservice.model.Order;
import ru.gbf.orderservice.service.OrderService;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    @Operation(summary = "Создание товара")
    public Order add(@RequestBody OrderDTO dto) throws URISyntaxException {
         return service.create(dto);
    }


}
