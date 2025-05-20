package com.store.store.controller;

import com.store.store.dto.ConstructionDTO;
import com.store.store.dto.ItemDTO;
import com.store.store.model.Construction;
import com.store.store.model.Item;
import com.store.store.repository.ConstructionRepository;
import com.store.store.repository.ItemRepository;
import com.store.store.service.ConstructionService;
import com.store.store.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @GetMapping("/constructions")
    public List<ConstructionDTO> getConstructions() {
        List<Construction> constructions = new ArrayList<>();
        cr.findAll().forEach(constructions::add);

        return constructions.stream()
                .map(ConstructionDTO::new)
                .collect(Collectors.toList());
    }



    @PostMapping("/newItem")
    public Item addItem(@RequestBody Item item) {
        return ir.save(item); //return as Json
    }

    @PostMapping("/add/{quantity}/{itemRef}")
    public ResponseEntity<ItemDTO> addItemToStockByRef(
            @PathVariable double quantity,
            @PathVariable int itemRef
    ) {
        Optional<Item> optionalItem = ir.findByItemRef(itemRef);
        if (optionalItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Item item = optionalItem.get();
        Item savedItem = itemService.addQuantityToStock(item.getId(), quantity);

        ItemDTO dto = new ItemDTO(
                savedItem.getId(),
                savedItem.getName(),
                savedItem.getQuantity(),
                savedItem.getPrice(),
                savedItem.getItemRef()
        );
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/sub/{quantity}/{itemRef}")
    public ResponseEntity<ItemDTO> subItemFromStockByRef(
            @PathVariable double quantity,
            @PathVariable int itemRef
    ) {
        Optional<Item> optionalItem = ir.findByItemRef(itemRef);
        if (optionalItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Item item = optionalItem.get();
        Item savedItem = itemService.subQuantityFromStock(item.getId(), quantity);

        ItemDTO dto = new ItemDTO(
                savedItem.getId(),
                savedItem.getName(),
                savedItem.getQuantity(),
                savedItem.getPrice(),
                savedItem.getItemRef()
        );
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/construction/{constID}/list")
    public List<ItemDTO> getItemList(@PathVariable int constID) {
        return constructionService.getConstructionItems(constID);

    }

    @PostMapping("/construction/{constID}/add/{itemRef}/{qnt}")
    public ResponseEntity<ItemDTO> addItemToConstructionByRef(
            @PathVariable int constID,
            @PathVariable int itemRef,
            @PathVariable Double qnt) {

        Optional<Item> optionalItem = ir.findByItemRef(itemRef);
        if (optionalItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Item item = optionalItem.get();
        ItemDTO result = constructionService.addQuantityToConstruction(item.getId(), constID, qnt);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/construction/{constID}/sub/{itemRef}/{qnt}")
    public ResponseEntity<ItemDTO> subItemFromConstructionByRef(
            @PathVariable int constID,
            @PathVariable int itemRef,
            @PathVariable Double qnt) {

        Optional<Item> optionalItem = ir.findByItemRef(itemRef);
        if (optionalItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Item item = optionalItem.get();
        ItemDTO result = constructionService.subQuantityFromConstruction(item.getId(), constID, qnt);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/newConstruction")
    public Construction addConstruction(@RequestBody Construction c) {
        return cr.save(c);
    }

    @GetMapping("/item/{itemRef}")
    public ResponseEntity<ItemDTO> getItemByRef(@PathVariable int itemRef) {
        Optional<Item> optionalItem = ir.findByItemRef(itemRef);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            ItemDTO dto = new ItemDTO(
                    item.getId(),
                    item.getName(),
                    item.getQuantity(),
                    item.getPrice(),
                    item.getItemRef()
            );
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}


