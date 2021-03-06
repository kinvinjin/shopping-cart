package com.kinvinjin.shoppingcart.service;

import com.kinvinjin.shoppingcart.dto.OrderItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PromotionServiceTest {
    @InjectMocks
    PromotionService promotionService;

    @Test
    void testMacBookProPromotion1() {
        Map<String, OrderItem> order = new HashMap<>();
        order.put(PromotionItem.MACBOOK_PRO.getSku(), OrderItem.builder().price(BigDecimal.valueOf(5399.99)).qty(BigDecimal.ONE).build());
        order.put(PromotionItem.RASPBERRY_PI_B.getSku(), OrderItem.builder().price(BigDecimal.valueOf(30.00)).qty(BigDecimal.ONE).build());
        BigDecimal cost = order.values().stream().map(r -> r.getPrice().multiply(r.getQty()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(BigDecimal.valueOf(5399.99), promotionService.applyMacBookProPromotion(order, cost));
    }

    @Test
    void testMacBookProPromotion2() {
        Map<String, OrderItem> order = new HashMap<>();
        order.put(PromotionItem.MACBOOK_PRO.getSku(), OrderItem.builder().price(BigDecimal.valueOf(5399.99)).qty(BigDecimal.ONE).build());
        BigDecimal cost = order.values().stream().map(r -> r.getPrice().multiply(r.getQty()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(BigDecimal.valueOf(5399.99), promotionService.applyMacBookProPromotion(order, cost));
    }

    @Test
    void testMacBookProPromotion3() {
        Map<String, OrderItem> order = new HashMap<>();
        order.put(PromotionItem.RASPBERRY_PI_B.getSku(), OrderItem.builder().price(BigDecimal.valueOf(30.00)).qty(BigDecimal.ONE).build());
        BigDecimal cost = order.values().stream().map(r -> r.getPrice().multiply(r.getQty()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(BigDecimal.valueOf(30.00), promotionService.applyMacBookProPromotion(order, cost));
    }

    @Test
    void testMacBookProPromotion4() {
        Map<String, OrderItem> order = new HashMap<>();
        order.put(PromotionItem.MACBOOK_PRO.getSku(), OrderItem.builder().price(BigDecimal.valueOf(5399.99)).qty(BigDecimal.ONE).build());
        order.put(PromotionItem.RASPBERRY_PI_B.getSku(), OrderItem.builder().price(BigDecimal.valueOf(30.00)).qty(BigDecimal.valueOf(2)).build());
        BigDecimal cost = order.values().stream().map(r -> r.getPrice().multiply(r.getQty()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(BigDecimal.valueOf(5399.99+30.00), promotionService.applyMacBookProPromotion(order, cost));
    }

    @Test
    void testGoogleHomePromotion1() {
        Map<String, OrderItem> order = new HashMap<>();
        order.put(PromotionItem.GOOGLE_HOME.getSku(), OrderItem.builder().price(BigDecimal.valueOf(49.99)).qty(BigDecimal.valueOf(3)).build());
        BigDecimal cost = order.values().stream().map(r -> r.getPrice().multiply(r.getQty()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(BigDecimal.valueOf(49.99*2), promotionService.applyGoogleHomePromotion(order, cost));
    }

    @Test
    void testGoogleHomePromotion2() {
        Map<String, OrderItem> order = new HashMap<>();
        order.put(PromotionItem.GOOGLE_HOME.getSku(), OrderItem.builder().price(BigDecimal.valueOf(49.99)).qty(BigDecimal.valueOf(2)).build());
        BigDecimal cost = order.values().stream().map(r -> r.getPrice().multiply(r.getQty()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(BigDecimal.valueOf(49.99*2), promotionService.applyGoogleHomePromotion(order, cost));
    }

    @Test
    void testGoogleHomePromotion3() {
        Map<String, OrderItem> order = new HashMap<>();
        order.put(PromotionItem.MACBOOK_PRO.getSku(), OrderItem.builder().price(BigDecimal.valueOf(5399.99)).qty(BigDecimal.ONE).build());
        BigDecimal cost = order.values().stream().map(r -> r.getPrice().multiply(r.getQty()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(BigDecimal.valueOf(5399.99), promotionService.applyGoogleHomePromotion(order, cost));
    }

    @Test
    void testAlexaSpeakerPromotion1() {
        Map<String, OrderItem> order = new HashMap<>();
        order.put(PromotionItem.ALEXA_SPEAKER.getSku(), OrderItem.builder().price(BigDecimal.valueOf(109.50)).qty(BigDecimal.valueOf(3)).build());
        BigDecimal cost = order.values().stream().map(r -> r.getPrice().multiply(r.getQty()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(BigDecimal.valueOf(109.50*3*0.9).setScale(2, RoundingMode.HALF_UP), promotionService.applyAlexaSpeakerPromotion(order, cost));
    }

    @Test
    void testAlexaSpeakerPromotion2() {
        Map<String, OrderItem> order = new HashMap<>();
        order.put(PromotionItem.ALEXA_SPEAKER.getSku(), OrderItem.builder().price(BigDecimal.valueOf(109.50)).qty(BigDecimal.valueOf(2)).build());
        BigDecimal cost = order.values().stream().map(r -> r.getPrice().multiply(r.getQty()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(BigDecimal.valueOf(109.50*2), promotionService.applyAlexaSpeakerPromotion(order, cost));
    }

    @Test
    void testAlexaSpeakerPromotion3() {
        Map<String, OrderItem> order = new HashMap<>();
        order.put(PromotionItem.MACBOOK_PRO.getSku(), OrderItem.builder().price(BigDecimal.valueOf(5399.99)).qty(BigDecimal.ONE).build());
        BigDecimal cost = order.values().stream().map(r -> r.getPrice().multiply(r.getQty()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(BigDecimal.valueOf(5399.99), promotionService.applyAlexaSpeakerPromotion(order, cost));
    }

    @Test
    void testApply() {
        Map<String, OrderItem> order = new HashMap<>();
        order.put(PromotionItem.MACBOOK_PRO.getSku(), OrderItem.builder().price(BigDecimal.valueOf(5399.99)).qty(BigDecimal.ONE).build());
        order.put(PromotionItem.RASPBERRY_PI_B.getSku(), OrderItem.builder().price(BigDecimal.valueOf(30.00)).qty(BigDecimal.ONE).build());
        order.put(PromotionItem.GOOGLE_HOME.getSku(), OrderItem.builder().price(BigDecimal.valueOf(49.99)).qty(BigDecimal.valueOf(3)).build());
        order.put(PromotionItem.ALEXA_SPEAKER.getSku(), OrderItem.builder().price(BigDecimal.valueOf(109.50)).qty(BigDecimal.valueOf(3)).build());

        assertEquals(BigDecimal.valueOf(5399.99+49.99*2+109.50*3*0.9).setScale(2, RoundingMode.HALF_UP),
                promotionService.apply(order));
    }
}
