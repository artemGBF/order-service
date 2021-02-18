package ru.gbf.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.gbf.orderservice.dto.CreateOrderEmailDto;
import ru.gbf.orderservice.dto.OrderDTO;
import ru.gbf.orderservice.dto.StockGoodDto;
import ru.gbf.orderservice.errors.ResourceLackException;
import ru.gbf.orderservice.model.CartGood;
import ru.gbf.orderservice.model.Order;
import ru.gbf.orderservice.repository.OrderRepository;
import ru.gbf.orderservice.types.OrderStatus;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final JmsTemplate jmsTemplate;
    @Value("${destination.queue}")
    private String destinationQueue;

    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    public Order create(OrderDTO dto) throws URISyntaxException {
        List<CartGood> goods = dto.getGoods();
        List<Long> collect = goods.stream().map(CartGood::getIdGood).collect(Collectors.toList());
        Map<Long, Integer> map = restTemplate.postForObject(
                "http://localhost:8080/api/stockpile/checkAllCount",
                collect,
                Map.class
        );
        for (int i = 0; i < goods.size(); i++) {
            Long idGood = goods.get(i).getIdGood();
            int count = goods.get(i).getCount();
            if (map.get(idGood.toString()) < count) {
                throw new ResourceLackException(idGood, count);
            }
        }
        restTemplate.patchForObject(new URI("http://localhost:8082/api/cart/clear"), dto.getIdCart(), Void.class);
        Order newOrder = repository.save(new Order(
                        null,
                        UUID.randomUUID().toString(),
                        dto.getIdUser(),
                        dto.getIdCart(),
                        dto.getDeliveryType(),
                        dto.getPayType(),
                        OrderStatus.PAYED
                )
        );
        List<StockGoodDto> stockGoodDtos = new ArrayList<>();
        for (int i = 0; i < goods.size(); i++) {
            CartGood cartGood = goods.get(i);
            stockGoodDtos.add(
                    new StockGoodDto(
                            cartGood.getIdGood(),
                            1L,
                            cartGood.getCount()
                    )
            );
        }
        restTemplate.postForObject(
                "http://localhost:8080/api/stockpile/order",
                stockGoodDtos,
                Void.class
        );
        jmsTemplate.convertAndSend(destinationQueue, new CreateOrderEmailDto(
                newOrder.getUuid(),
                newOrder.getDelivery().name(),
                dto.getIdCart()
        ));
        return newOrder;
    }


}
