package ru.gbf.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gbf.orderservice.types.DeliveryType;
import ru.gbf.orderservice.types.PayType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {
    @Schema(description = "id пользователя", required = true)
    private Long idUser;
    private Long idCart;
    private DeliveryType deliveryType;
    private PayType payType;
}
