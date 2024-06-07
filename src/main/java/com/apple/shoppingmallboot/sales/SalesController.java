package com.apple.shoppingmallboot.sales;

import com.apple.shoppingmallboot.member.CustomUser;
import com.apple.shoppingmallboot.member.Member;
import com.apple.shoppingmallboot.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;
    private final MemberService memberService;

    @PostMapping("/order")
    String postOrder(@ModelAttribute Sales sales,
                     Authentication auth){
        CustomUser user = (CustomUser)auth.getPrincipal();
        var member = new Member();
        member.setId(user.getId());
        sales.setMember(member);

        salesService.saveSales(sales);

        return "redirect:/list/page/1";
    }

    @GetMapping("/order/all")
    String getOrderAll(Model model){
        List<SalesDto> resultList = salesService.customFindAll();

        model.addAttribute("datas", resultList);

        return "sales.html";
    }
}
