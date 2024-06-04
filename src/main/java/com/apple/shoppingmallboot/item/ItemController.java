package com.apple.shoppingmallboot.item;

import com.apple.shoppingmallboot.comment.Comment;
import com.apple.shoppingmallboot.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CommentService commentService;
    private final S3Service s3Service;

    @GetMapping("/list")
    String list(Model model) {
        List<Item> result = itemService.findAllItem();
        model.addAttribute("items", result);

        return "list.html";
    }

    @GetMapping("/list/page/{page}")
    String list(Model model, @PathVariable Integer page) {
        Page<Item> result = itemService.pageNation(page - 1,5);
//        Slice<Item> result = itemService.slicePageNation(page -1, 5);
        int resultCnt = result.getTotalPages();
        model.addAttribute("items", result);
        model.addAttribute("totalPage",resultCnt);

        return "list.html";
    }

    @GetMapping("/write")
    String write(){
        return "write.html";
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    String addPost(@ModelAttribute Item item, Authentication auth){
        item.setCreateBy(auth.getName());
        itemService.saveItem(item);
        return "redirect:/list/page/1";
    }

    @GetMapping("/detail/{id}/page/{page}")
    String detail(@PathVariable Integer page,@PathVariable Long id, Model model) throws Exception {

        // Optional : 변수가 비어있을 수도 있고 Item 일 수도 있는 클래스
        Optional<Item> result = itemService.findById(id);
        Page<Comment> commentResult = commentService.findByParentId(page - 1,3);
        if(result.isPresent()) {
            model.addAttribute("data", result.get());
            if(!commentResult.isEmpty()){
                model.addAttribute("comments",commentResult);
                model.addAttribute("commentsCnt",commentResult.getTotalPages());
            }
        } else {
            return "redirect:/list/page/1";
        }
        return "detail.html";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    String edit(@PathVariable Long id, Model model){
        Optional<Item> result = itemService.findById(id);
        if(result.isPresent()) {
            model.addAttribute("data", result.get());
            return "edit.html";
        } else {
            return "redirect:/list/page/1";
        }
    }

    @PutMapping("/edit")
    String updateSaveItem(@ModelAttribute Item item){
        itemService.saveItem(item);
        return "redirect:/list/page/1";
    }

    @GetMapping ("/test1")
    String test1(@RequestParam String name, @RequestParam int age){
        System.out.println(name + age + "요청들어옴");
        return "redirect:/list/page/1";
    }

    @DeleteMapping("/item")
    @ResponseBody
    ResponseEntity deleteItem(@RequestParam Long id){
        itemService.deleteItem(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename){
        var result = s3Service.createPresignedUrl("test/" + filename);
        return result;
    }

}
