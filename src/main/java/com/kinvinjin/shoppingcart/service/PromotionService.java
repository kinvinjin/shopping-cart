package com.kinvinjin.shoppingcart.service;

import com.kinvinjin.shoppingcart.dto.OrderItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;

import static com.kinvinjin.shoppingcart.service.ItemEnum.*;

@Service
public class PromotionService {
    public BigDecimal applyMacBookProPromotion(Map<ItemEnum, OrderItem> order, BigDecimal cost) {
        OrderItem macBookPro = order.get(MACBOOK_PRO);
        OrderItem raspberryPiB = order.get(RASPBERRY_PI_B);
        if (macBookPro == null || raspberryPiB == null) {
            return cost;
        }

        BigDecimal promoQty = macBookPro.getQty().compareTo(raspberryPiB.getQty()) >=0 ?
                raspberryPiB.getQty() : macBookPro.getQty();

        return cost.subtract(raspberryPiB.getPrice().multiply(promoQty));
    }

    public BigDecimal applyGoogleHomePromotion(Map<ItemEnum, OrderItem> order, BigDecimal cost) {
        OrderItem itemOrder = order.get(GOOGLE_HOME);
        if (itemOrder == null) {
            return cost;
        }

        BigDecimal promoQty = itemOrder.getQty().divide(BigDecimal.valueOf(3), new MathContext(2, RoundingMode.FLOOR))
                .setScale(0, RoundingMode.FLOOR);

        return cost.subtract(itemOrder.getPrice().multiply(promoQty));
    }

    public BigDecimal applyAlexaSpeakerPromotion(Map<ItemEnum, OrderItem> order, BigDecimal cost) {
        OrderItem itemOrder = order.get(ALEXA_SPEAKER);
        if (itemOrder == null) {
            return cost;
        }

        if (itemOrder.getQty().compareTo(BigDecimal.valueOf(3)) >= 0) {
            return cost.subtract(itemOrder.getQty().multiply(itemOrder.getPrice().multiply(BigDecimal.valueOf(0.1))));
        } else {
            return cost;
        }
    }

    public BigDecimal apply(Map<ItemEnum, OrderItem> order) {
        BigDecimal cost = order.values().stream().map(r -> r.getPrice().multiply(r.getQty()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cost = applyMacBookProPromotion(order, cost);
        cost = applyGoogleHomePromotion(order, cost);
        cost = applyAlexaSpeakerPromotion(order, cost);

        return cost.compareTo(BigDecimal.ZERO) > 0 ? cost.setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
    }
}
