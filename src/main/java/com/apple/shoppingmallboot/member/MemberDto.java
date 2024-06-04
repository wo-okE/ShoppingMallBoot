package com.apple.shoppingmallboot.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private Long id;
    private String username;
    private String displayName;

    MemberDto(String username, String displayName){
        this.username = username;
        this.displayName = displayName;
    }

    public MemberDto(String username, String displayName, Long id) {
        this.username = username;
        this.displayName = displayName;
        this.id = id;
    }
}
