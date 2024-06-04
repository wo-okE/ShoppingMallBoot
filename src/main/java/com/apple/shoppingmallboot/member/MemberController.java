package com.apple.shoppingmallboot.member;

import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Optional;

@Controller
// @PreAuthorize("isAuthenticated()") 어노테이션 안의 메소드가 참인 경우에만 실행 (클래스, 메소드 모두 가능)
// =======================================
// @PreAuthorize("isAuthenticated()")
// @PreAuthorize("isAnonymous()")
// @PreAuthorize("hasAuthority('어쩌구')")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    String registerView(Authentication auth){
        if(auth.isAuthenticated()){
            return "redirect:/list";
        }else {
            return "register.html";
        }
    }

    @PostMapping("/member")
    String registerMember(@ModelAttribute Member member){
         member.setPassword(passwordEncoder.encode(member.getPassword())); // 비밀번호 해싱
         memberService.saveMember(member);
         return "list.html";
    }

    @GetMapping("/login")
    String login(){
        return "login.html";
    }

    @GetMapping("/my-page")
    public String myPage(Authentication auth){
        CustomUser result = (CustomUser) auth.getPrincipal();
        if(auth.isAuthenticated()){
            return "mypage.html";
        }
        return "redirect:/login";
    }


    @GetMapping("/user/1")
    @ResponseBody
    public MemberDto getUser() {
        var a = memberService.findByIdUser(1L);
        var result = a.get();
        var data = new MemberDto(result.getUsername(), result.getDisplayName(), result.getId());

        return data;
    }
}
