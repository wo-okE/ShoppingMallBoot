package com.apple.shoppingmallboot.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findPageBy(Pageable page);

    Slice<Item> findSliceBy(Pageable page);

    Page<Item> findAllByTitleContains(Pageable page, String searchText);

    @Query(value = "select * from item where match(title) against(?1)", nativeQuery = true)
    Page<Item> rawQuery1(String searchText, Pageable page);
}
