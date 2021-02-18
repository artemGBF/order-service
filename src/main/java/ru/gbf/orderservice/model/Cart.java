package ru.gbf.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("carts")
public class Cart {
    @Id
    private Long id;
    private String uuid;
    private LocalDateTime dateTime;
    private Long idUser;
}
