package com.apple.shoppingmallboot.sales;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class SalesDto {
    private String itemName;
    private Integer price;
    private String userName;
    private String displayName;
    private LocalDateTime created;
}
