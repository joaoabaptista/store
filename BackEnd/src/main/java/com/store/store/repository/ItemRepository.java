package com.store.store.repository;

import com.store.store.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Integer> {
    Optional<Item> findByItemRef(int itemRef);
}
