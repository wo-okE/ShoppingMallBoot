package com.apple.shoppingmallboot.member;

import com.apple.shoppingmallboot.sales.Sales;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, updatable = false, unique = true) private String username;
    @Column(nullable = false) private String password;
    @Column(nullable = false) private String displayName;

    @ToString.Exclude
    @OneToMany(mappedBy = "member")
    List<Sales> sales = new ArrayList<>();
}
