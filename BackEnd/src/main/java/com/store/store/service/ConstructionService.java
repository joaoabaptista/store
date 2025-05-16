package com.store.store.service;

import com.store.store.dto.ItemDTO;
import com.store.store.model.Construction;
import com.store.store.model.ConstructionItem;
import com.store.store.model.Item;
import com.store.store.repository.ConstructionItemRepository;
import com.store.store.repository.ConstructionRepository;
import com.store.store.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConstructionService {

    @Autowired
    private ItemRepository ir;

    @Autowired
    private ConstructionRepository cr;

    @Autowired
    private ConstructionItemRepository cir;

    @Autowired
    private ItemService is;

    @Transactional(rollbackFor = Exception.class)
    public ItemDTO addQuantityToConstruction(int itemId, int constructionId, Double qnt) {
        System.out.println(qnt);
        if (qnt == null || qnt <= 0) {
            throw new IllegalArgumentException("Quantidade inválida: " + qnt);
        }

        Construction cons = cr.findById(constructionId)
                .orElseThrow(() -> new RuntimeException("Construction not found"));
        Item item = ir.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        is.subQuantityFromStock(itemId, qnt); //remove from stock

        Optional<ConstructionItem> optionalConstructionItem = cir .findByConstructionIdAndItemId(constructionId, itemId);

        ConstructionItem constructionItem = optionalConstructionItem.orElseGet(() -> {
            ConstructionItem newItem = new ConstructionItem();
            newItem.setItem(item);
            newItem.setConstruction(cons);
            newItem.setQuantity(0.0);

            // Adiciona o novo item na coleção da construção e do item, se for necessário manter o relacionamento
            cons.getConstructionItems().add(newItem);
            item.getConstructionItems().add(newItem);

            return newItem;
        });

        constructionItem.setQuantity(constructionItem.getQuantity() + qnt);

        // Como o ConstructionItem está ligado ao Construction, e você alterou a quantidade, pode salvar direto o constructionItem
        cir.save(constructionItem);

        return new ItemDTO(
                item.getId(),
                item.getName(),
                constructionItem.getQuantity(),
                item.getPrice().doubleValue() * constructionItem.getQuantity(),
                item.getItemRef()
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public ItemDTO subQuantityFromConstruction(int itemId, int constructionId, Double qnt) {
        if (qnt == null || qnt <= 0) {
            throw new IllegalArgumentException("Quantidade inválida: " + qnt);
        }

        Construction cons = cr.findById(constructionId)
                .orElseThrow(() -> new RuntimeException("Construction not found"));
        Item item = ir.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Optional<ConstructionItem> optionalConstructionItem = cir.findByConstructionIdAndItemId(constructionId, itemId);

        ConstructionItem constructionItem = optionalConstructionItem
                .orElseThrow(() -> new RuntimeException("Item não encontrado na construção"));

        Double newQuantity = constructionItem.getQuantity() - qnt;
        if (newQuantity < 0) {
            throw new RuntimeException("Não é possível retirar mais do que a quantidade disponível na construção");
        }

        // Primeiro subtrai da construção
        constructionItem.setQuantity(newQuantity);
        cir.save(constructionItem);

        // Depois adiciona ao estoque
        is.addQuantityToStock(itemId, qnt);

        return new ItemDTO(
                item.getId(),
                item.getName(),
                constructionItem.getQuantity(),
                item.getPrice().doubleValue() * constructionItem.getQuantity(),
                item.getItemRef()
        );
    }


    public List<ItemDTO> getConstructionItems(int constructionId) {
        Construction cons = cr.findById(constructionId)
            .orElseThrow(() -> new RuntimeException("Construction not found"));

        return cons.getConstructionItems().stream()
                .map(ci -> {
                    Item i = ci.getItem();
                    Double unitPrice = i.getPrice().doubleValue();
                    Double quantity = ci.getQuantity();
                    Double total = quantity * unitPrice;

                    return new ItemDTO(
                            i.getId(),
                            i.getName(),
                            i.getQuantity(),
                            total,
                            i.getItemRef()
                    );
                })
                .collect(Collectors.toList());
    }
}
