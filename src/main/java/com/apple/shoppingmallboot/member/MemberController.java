package com.apple.shoppingmallboot.member;

import com.apple.shoppingmallboot.config.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtil jwtUtil;

    @GetMapping("/register")
    String registerView(Authentication auth){
        if(auth.isAuthenticated()){
            return "redirect:/list/page/1";
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

    @PostMapping("/login/jwt")
    @ResponseBody
    public String loginJWT(@RequestBody Map<String, String> data,
                           HttpServletResponse response){

        var authToken = new UsernamePasswordAuthenticationToken(
                data.get("username"),data.get("password")
        );

        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        var jwt = jwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());

        var cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(10);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return jwt;
    }

    @GetMapping("/my-page/jwt")
    @ResponseBody
    String myPageJWT(Authentication auth) {
        auth = SecurityContextHolder.getContext().getAuthentication();
        var user = (CustomUser) auth.getPrincipal();
        System.out.println(user.toString());
        System.out.println(user.getDisplayName().toString());
        System.out.println(user.getAuthorities().toString());


        return "마이페이지데이터";
    }
}
