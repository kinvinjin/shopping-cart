package com.kinvinjin.shoppingcart.service;

import com.kinvinjin.shoppingcart.dto.OrderItem;
import com.kinvinjin.shoppingcart.model.Inventory;
import com.kinvinjin.shoppingcart.model.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CheckoutServiceTest {
    @InjectMocks
    CheckoutService checkoutService;

    @Mock
    PromotionService promotionService;

    @Mock
    InventoryRepository inventoryRepository;

    private Inventory getInventoryItem(Long qty) {
        Inventory item = new Inventory();
        item.setId(1);
        item.setSku(PromotionItem.MACBOOK_PRO.getSku());
        item.setName(PromotionItem.MACBOOK_PRO.getName());
        item.setQty(BigDecimal.valueOf(qty));
        return item;
    }

    @Test
    void test1() {
        when(inventoryRepository.findBySku(any())).thenReturn(getInventoryItem(2L));
        Map<String, OrderItem> order = checkoutService.scan("43N23P,43N23P");
        assertEquals(BigDecimal.valueOf(2), order.get(PromotionItem.MACBOOK_PRO.getSku()).getQty());
    }

    @Test
    void test2() {
        when(inventoryRepository.findBySku(any())).thenReturn(getInventoryItem(1L));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.scan("43N23P,43N23P");
        });
        assertEquals("insufficient inventory quantity for MacBook Pro", exception.getMessage());
    }

    @Test
    void test3() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.scan("43N23P,INVALID_CODE");
        });
        assertEquals("unknown item: INVALID_CODE", exception.getMessage());
    }
}
