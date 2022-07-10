package com.kinvinjin.shoppingcart.service;

import com.kinvinjin.shoppingcart.dto.OrderItem;
import com.kinvinjin.shoppingcart.model.Inventory;
import com.kinvinjin.shoppingcart.model.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.EnumMap;
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

    public Map<ItemEnum, OrderItem> scan(String orderList) throws IllegalArgumentException {
        Map<ItemEnum, Long> orderMap = Arrays.stream(orderList.split(","))
                .map(r -> ItemEnum.lookup(r.trim()))
                .collect(groupingBy(Function.identity(), () -> new EnumMap<ItemEnum, Long>(ItemEnum.class),Collectors.counting()));
        Map<ItemEnum, OrderItem> order = new EnumMap<>(ItemEnum.class);

        for (Map.Entry<ItemEnum, Long> entry : orderMap.entrySet()) {
            ItemEnum item = entry.getKey();
            BigDecimal qty = BigDecimal.valueOf(entry.getValue());
            Inventory inventoryItem = inventoryRepository.findBySku(item.getSku());

            if (inventoryItem.getQty().compareTo(BigDecimal.ZERO) == 0 ||
                    inventoryItem.getQty().compareTo(qty) < 0) {
                throw new IllegalArgumentException("insufficient inventory quantity for " + item.getName());
            }
            order.put(item, OrderItem.builder().price(inventoryItem.getPrice()).qty(qty).build());
        }

        return order;
    }
    public BigDecimal calculate(String orderList) throws IllegalArgumentException {
        Map<ItemEnum, OrderItem> order = scan(orderList);
        return promotionService.apply(order);
    }
}
