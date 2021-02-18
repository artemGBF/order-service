package ru.gbf.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderEmailDto extends EmailDto {
    private String number;
    private String delivery;
    private Long idCart;



}