package com.store.store.service;

import com.store.store.model.Item;
import com.store.store.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item addQuantity(int id, Long quantity) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found") );

        item.setQuantity(item.getQuantity() + quantity);
        return itemRepository.save(item);
    }

    public Item subQuantity(int id, Long quantity) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found") );

        if (item.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock to subtract");
        }

        item.setQuantity(item.getQuantity() - quantity);
        if (item.getQuantity() <= item.getMinimumStock()) {
            System.out.println("Low stock: " + item.getName());
        }

        return itemRepository.save(item);
    }
}
