package com.store.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntermediateService {

    @Autowired
    private ItemService is;

    @Autowired
    private ConstructionService cons;

    public void addItemToConstruction(int itemId, int constructionId, Double qnt){
        is.subQuantityFromStock(itemId, qnt);
        cons.addQuantityToConstruction(itemId, constructionId, qnt);
    }

    public void subItemFromConstruction(int itemId, int constructionId, Double qnt){
        cons.subQuantityFromConstruction(itemId, constructionId, qnt);
        is.addQuantityToStock(itemId, qnt);
    }
}

