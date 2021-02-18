package ru.gbf.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gbf.orderservice.model.Cart;
import ru.gbf.orderservice.model.CartGood;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDTO {
    private Long id;
    private String uuid;
    private List<CartGood> cartGood;
    private Long idUser;

    public CartDTO(Cart cart, List<CartGood> goods) {
        this.id = cart.getId();
        this.uuid = cart.getUuid();
        this.cartGood = goods;
        this.idUser=cart.getIdUser();
    }
}
