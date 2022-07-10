package com.kinvinjin.shoppingcart.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItem {
    private BigDecimal price;
    private BigDecimal qty;
}
