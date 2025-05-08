package com.store.store.controller;

import com.store.store.dto.ConstructionItemDTO;
import com.store.store.model.Construction;
import com.store.store.model.Item;
import com.store.store.repository.ConstructionRepository;
import com.store.store.repository.ItemRepository;
import com.store.store.service.ConstructionService;
import com.store.store.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ItemController {

    @Autowired
    private ItemRepository ir;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ConstructionService constructionService;
    @Autowired
    private ConstructionRepository cr;

    @GetMapping("/items")
    public Iterable<Item> getStock() {
        return ir.findAll();
    }

    @PostMapping("/newItem")
    public Item addItem(@RequestBody Item item) {
        return ir.save(item); //return as Json
    }

    @PutMapping("/add/{qnt}/{id}")
    public Item addToItem(@PathVariable int id, @PathVariable Double qnt) {
        return itemService.addQuantityToStock(id, qnt);
    }

    @PutMapping("/sub/{id}/{qnt}")
    public Item subQuantity(@PathVariable int id, @PathVariable Double qnt) {
        return itemService.subQuantityFromStock(id, qnt);
    }

    @GetMapping("/construction/{constID}/list")
    public List<ConstructionItemDTO> getItemList(@PathVariable int constID) {
        return constructionService.getConstructionItems(constID);

    }

    @PostMapping("/construction/{constID}/add/{itemID}/{qnt}")
    public ResponseEntity<ConstructionItemDTO> addItemToConstruction(
            @PathVariable int constID,
            @PathVariable int itemID,
            @PathVariable Double qnt) {

        ConstructionItemDTO result = constructionService.addQuantityToConstruction(itemID, constID, qnt);
        return ResponseEntity.ok(result); // ou status 201 CREATED se for nova ligação
    }

    @PostMapping("/construction/{constID}/sub/{itemID}/{qnt}")
    public ResponseEntity<ConstructionItemDTO> subItemToConstruction(
            @PathVariable int constID,
            @PathVariable int itemID,
            @PathVariable Double qnt) {

        ConstructionItemDTO result = constructionService.subQuantityFromConstruction(itemID, constID, qnt);
        return ResponseEntity.ok(result); // ou status 201 CREATED se for nova ligação
    }

    @PostMapping("/newConstruction")
    public Construction addConstruction(@RequestBody Construction c) {
        return cr.save(c);
    }

}


