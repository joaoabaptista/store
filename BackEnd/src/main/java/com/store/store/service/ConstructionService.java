package com.store.store.service;

import com.store.store.dto.ConstructionItemDTO;
import com.store.store.model.Construction;
import com.store.store.model.ConstructionItem;
import com.store.store.model.Item;
import com.store.store.repository.ConstructionRepository;
import com.store.store.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConstructionService {

    @Autowired
    private ItemRepository ir;

    @Autowired
    private ConstructionRepository cr;


    public ConstructionItemDTO addQuantityToConstruction(int itemId, int constructionId, Double qnt) {
        Construction cons = cr.findById(constructionId)
                .orElseThrow(() -> new RuntimeException("Construction not found"));
        Item item = ir.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        ConstructionItem constructionItem = cons.getConstructionItems().stream()
                .filter(ci -> ci.getItem().getId() == itemId)
                .findFirst()
                .orElseGet(() -> {
                    ConstructionItem newItem = new ConstructionItem();
                    newItem.setItem(item);
                    newItem.setConstruction(cons);
                    newItem.setQuantity(0.0);
                    cons.getConstructionItems().add(newItem);
                    item.getConstructionItems().add(newItem);
                    return newItem;
                });
        constructionItem.setQuantity(constructionItem.getQuantity() + qnt);
        cr.save(cons);

        return new ConstructionItemDTO(
                item.getItemRef(),
                item.getName(),
                constructionItem.getQuantity(),
                item.getPrice().doubleValue() * constructionItem.getQuantity()
        );
    }

    public ConstructionItemDTO subQuantityFromConstruction(int itemId, int constructionId, Double qnt) {
        Construction cons = cr.findById(constructionId)
                .orElseThrow(() -> new RuntimeException("Construction not found"));
        Item item = ir.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        ConstructionItem constructionItem = cons.getConstructionItems().stream()
                .filter(ci -> ci.getItem().getId() == itemId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in the construction"));

        Double newQuantity = constructionItem.getQuantity() - qnt;
        if (newQuantity < 0) {
            throw new RuntimeException("New quantity is negative");
        }

        constructionItem.setQuantity(constructionItem.getQuantity() - qnt);
        cr.save(cons);

        return new ConstructionItemDTO(
                item.getItemRef(),
                item.getName(),
                constructionItem.getQuantity(),
                item.getPrice().doubleValue() * constructionItem.getQuantity()
        );
    }

    public List<ConstructionItemDTO> getConstructionItems(int constructionId) {
        Construction cons = cr.findById(constructionId)
            .orElseThrow(() -> new RuntimeException("Construction not found"));

        return cons.getConstructionItems().stream()
                .map(ci -> {
                    Item i = ci.getItem();
                    Double unitPrice = i.getPrice().doubleValue();
                    Double quantity = ci.getQuantity();
                    Double total = quantity * unitPrice;

                    return new ConstructionItemDTO(
                            i.getItemRef(),
                            i.getName(),
                            quantity,
                            total
                    );
                })
                .collect(Collectors.toList());
    }
}
