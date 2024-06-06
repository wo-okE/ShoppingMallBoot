package com.apple.shoppingmallboot.sales;

import com.apple.shoppingmallboot.member.CustomUser;
import com.apple.shoppingmallboot.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;
    @PostMapping("/order")
    String postOrder(@ModelAttribute Sales sales, Authentication auth){
//        Optional<Member> member = memberService.findByUserName(auth.getName());
//        sales.setMemberId(member.get().getId());
        CustomUser user = (CustomUser)auth.getPrincipal();
        sales.setId(user.getId());
        salesService.saveSales(sales);

        return "redirect:/list/page/1";
    }
}
