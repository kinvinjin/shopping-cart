package com.kinvinjin.shoppingcart.service;

import com.kinvinjin.shoppingcart.dto.OrderItem;
import com.kinvinjin.shoppingcart.model.Inventory;
import com.kinvinjin.shoppingcart.model.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class CheckoutService {
    @Autowired
    private PromotionService promotionService;

    @Autowired
    private InventoryRepository inventoryRepository;

    public Map<String, OrderItem> scan(String orderList) throws IllegalArgumentException {
        Map<String, Long> orderMap = Arrays.stream(orderList.split(",")).map(String::trim)
                .collect(groupingBy(Function.identity(), Collectors.counting()));
        Map<String, OrderItem> order = new HashMap<>();

        for (Map.Entry<String, Long> entry : orderMap.entrySet()) {
            BigDecimal qty = BigDecimal.valueOf(entry.getValue());
            Inventory inventoryItem = inventoryRepository.findBySku(entry.getKey());

            if (inventoryItem == null) {
                throw new IllegalArgumentException("unknown item: " + entry.getKey());
            }

            if (inventoryItem.getQty().compareTo(BigDecimal.ZERO) == 0 ||
                    inventoryItem.getQty().compareTo(qty) < 0) {
                throw new IllegalArgumentException("insufficient inventory quantity for " + inventoryItem.getName());
            }
            order.put(entry.getKey(), OrderItem.builder().price(inventoryItem.getPrice()).qty(qty).build());
        }

        return order;
    }
    public BigDecimal calculate(String orderList) throws IllegalArgumentException {
        Map<String, OrderItem> order = scan(orderList);
        return promotionService.apply(order);
    }
}
