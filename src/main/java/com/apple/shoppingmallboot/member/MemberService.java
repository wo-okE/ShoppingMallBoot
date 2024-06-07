package com.apple.shoppingmallboot.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    public Optional<Member> findByIdUser(Long id) { return memberRepository.findById(id); }

    public Optional<Member> findByUserName(String username) { return memberRepository.findByUsername(username); }

    public Optional<Member> findById(Long id) { return memberRepository.findById(id); }
}
