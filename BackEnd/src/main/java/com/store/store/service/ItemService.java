package com.store.store.service;

import com.store.store.model.Item;
import com.store.store.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Transactional(rollbackFor = Exception.class)
    public Item addQuantityToStock(int id, Double quantity) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found") );

        item.setQuantity(item.getQuantity() + quantity);
        return itemRepository.save(item);
    }

    @Transactional(rollbackFor = Exception.class)
    public Item subQuantityFromStock(int id, Double quantity) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        System.out.println("Estoque atual: " + item.getQuantity() + ", quantidade para subtrair: " + quantity);

        if (item.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock to subtract");
        }

        item.setQuantity(item.getQuantity() - quantity);

        Double minimumStock = item.getMinimumStock();
        if (minimumStock == null) {
            minimumStock = 0.0;
        }

        if (item.getQuantity() <= minimumStock) {
            System.out.println("Low stock: " + item.getName());
        }

        return itemRepository.save(item);
    }



}
