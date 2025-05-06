package com.store.store.controller;

import com.store.store.model.Item;
import com.store.store.repository.ItemRepository;
import com.store.store.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ItemController {

    @Autowired
    private ItemRepository ir;
    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public Iterable<Item> getStock() {
        return ir.findAll();
    }

    @PostMapping("/newItem")
    public Item addItem(@RequestBody Item item) {
        return ir.save(item); //return as Json
    }

    @PutMapping("/add/{qnt}/{id}")
    public Item addToItem(@PathVariable int id, @PathVariable Long qnt) {
        return itemService.addQuantity(id, qnt);
    }

    @PutMapping("sub/{id}/{qnt}")
    public Item subQuantity(@PathVariable int id, @PathVariable Long qnt) {
        return itemService.subQuantity(id, qnt);
    }
}
