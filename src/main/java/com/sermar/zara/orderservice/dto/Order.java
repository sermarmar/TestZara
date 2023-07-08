package com.sermar.zara.orderservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Order {

    private Long idOrder;
    private Long idUser;
    private String name;
    private Double subtotal;
    private Double total;
    private String address;

}
