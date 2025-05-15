package com.store.store.repository;

import com.store.store.model.ConstructionItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ConstructionItemRepository extends CrudRepository<ConstructionItem, Integer> {

    Optional<ConstructionItem> findByConstructionIdAndItemId(Integer constructionId, Integer itemId);

}
