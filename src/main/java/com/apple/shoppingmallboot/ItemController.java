package com.apple.shoppingmallboot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/list")
    String list(Model model) {
        List<Item> result = itemService.findAllItem();
        model.addAttribute("items", result);

        return "list.html";
    }

    @GetMapping("/write")
    String write(){
        return "write.html";
    }

    @PostMapping("/add")
    String addPost(@ModelAttribute Item item){
        itemService.saveItem(item);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) throws Exception {

        // Optional : 변수가 비어있을 수도 있고 Item 일 수도 있는 클래스
        Optional<Item> result = itemService.findById(id);
        if(result.isPresent()) {
            model.addAttribute("data", result.get());
        } else {
            return "redirect:/list";
        }
        return "detail.html";
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable Long id, Model model){
        Optional<Item> result = itemService.findById(id);
        if(result.isPresent()) {
            model.addAttribute("data", result.get());
            return "edit.html";
        } else {
            return "redirect:/list";
        }
    }

    @PutMapping("/update")
    void updateSaveItem(Item item){
        System.out.println(item);
    }
}
