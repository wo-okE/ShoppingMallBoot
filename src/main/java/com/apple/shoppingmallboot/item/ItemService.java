package com.apple.shoppingmallboot.item;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findAllItem(){
        return itemRepository.findAll();
    }

    public Optional<Item> findById(Long id){
        return itemRepository.findById(id);
    }

    public void deleteItem(Long id) { itemRepository.deleteById(id); }

    public Page<Item> pageNation(int page, int cnt){
        return itemRepository.findPageBy(PageRequest.of(page,cnt));
    }

    public Slice<Item> slicePageNation(int page, int cnt) {
        return itemRepository.findSliceBy(PageRequest.of(page,cnt));
    }
}
