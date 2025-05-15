package com.store.store.controller;

import com.store.store.dto.ConstructionItemDTO;
import com.store.store.dto.ItemDTO;
import com.store.store.model.Construction;
import com.store.store.model.ConstructionItem;
import com.store.store.model.Item;
import com.store.store.repository.ConstructionRepository;
import com.store.store.repository.ItemRepository;
import com.store.store.service.ConstructionService;
import com.store.store.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ItemDTO> getStock() {
        List<Item> items = (List<Item>) ir.findAll();
        return items.stream()
                .map(item -> new ItemDTO(
                        item.getId(),
                        item.getName(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getItemRef()
                ))
                .collect(Collectors.toList());
    }


    @PostMapping("/newItem")
    public Item addItem(@RequestBody Item item) {
        return ir.save(item); //return as Json
    }

    @PostMapping("/add/{quantity}/{itemId}")
    public ResponseEntity<ItemDTO> addItemToStock(
            @PathVariable double quantity,
            @PathVariable int itemId
    ) {
        Item savedItem = itemService.addQuantityToStock(itemId, quantity);

     //   Double totalPrice = savedItem.getPrice() * savedItem.getQuantity();

        ItemDTO dto = new ItemDTO(
                savedItem.getId(),
                savedItem.getName(),
                savedItem.getQuantity(),
                savedItem.getPrice(),
              //  totalPrice,
                savedItem.getItemRef()
        );
        return ResponseEntity.ok(dto);
    }


    @PostMapping("/sub/{quantity}/{itemId}")
    public ResponseEntity<ItemDTO> subItemFromStock(
            @PathVariable double quantity,
            @PathVariable int itemId
    ) {
        Item savedItem = itemService.subQuantityFromStock(itemId, quantity);

       // Double totalPrice = savedItem.getPrice() * savedItem.getQuantity();

        ItemDTO dto = new ItemDTO(
                savedItem.getId(),
                savedItem.getName(),
                savedItem.getQuantity(),
                savedItem.getPrice(),
               // totalPrice,
                savedItem.getItemRef()
        );
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/construction/{constID}/list")
    public List<ItemDTO> getItemList(@PathVariable int constID) {
        return constructionService.getConstructionItems(constID);

    }

    @PostMapping("/construction/{constID}/add/{itemID}/{qnt}")
    public ResponseEntity<ItemDTO> addItemToConstruction(
            @PathVariable int constID,
            @PathVariable int itemID,
            @PathVariable Double qnt) {

        ItemDTO result = constructionService.addQuantityToConstruction(itemID, constID, qnt);
        return ResponseEntity.ok(result); // ou status 201 CREATED se for nova ligação
    }

    @PostMapping("/construction/{constID}/sub/{itemID}/{qnt}")
    public ResponseEntity<ItemDTO> subItemToConstruction(
            @PathVariable int constID,
            @PathVariable int itemID,
            @PathVariable Double qnt) {

        ItemDTO result = constructionService.subQuantityFromConstruction(itemID, constID, qnt);
        return ResponseEntity.ok(result); // ou status 201 CREATED se for nova ligação
    }

    @PostMapping("/newConstruction")
    public Construction addConstruction(@RequestBody Construction c) {
        return cr.save(c);
    }

}


