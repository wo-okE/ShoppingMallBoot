package com.apple.shoppingmallboot.comment;

import com.apple.shoppingmallboot.member.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    String addComment(Comment comment, Authentication auth){
        CustomUser user = (CustomUser) auth.getPrincipal();
        if(auth.isAuthenticated()){
            comment.setUsername(user.getUsername());
            commentService.saveComment(comment);
            return "redirect:/detail/"+comment.getParentId()+"/page/1";
        } else {
            return "redirect:/list/page/1";
        }
    }
}
