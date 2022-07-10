package com.kinvinjin.shoppingcart.service;

import lombok.Getter;

@Getter
public enum ItemEnum {
    GOOGLE_HOME("120P90", "Google Home"),
    MACBOOK_PRO("43N23P", "MacBook Pro"),
    ALEXA_SPEAKER("A304SD", "Alexa Speaker"),
    RASPBERRY_PI_B("234234", "Raspberry Pi B");
    private String sku;
    private String name;
    ItemEnum(String sku, String name) {
        this.sku = sku;
        this.name = name;
    }

    public static ItemEnum lookup(String token) {
        for (ItemEnum item: ItemEnum.values()) {
            if (item.sku.equals(token) || item.name.equals(token)) {
                return item;
            }
        }
        throw new IllegalArgumentException("unknown item: " + token);
    }
}
