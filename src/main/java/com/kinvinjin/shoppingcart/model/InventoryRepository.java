package com.kinvinjin.shoppingcart.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Integer>{
    Inventory findBySku(String sku);
}
