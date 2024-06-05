package com.apple.shoppingmallboot.item;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@ToString
@EqualsAndHashCode
@Getter
@Table(indexes = @Index(columnList = "title", name = "titleIndex"))
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String title;
    @Column(nullable = false) private Integer price;
    private String imgUrl;
    @Column(nullable = false) private String createBy;

    public void setId(Long id) { this.id = id; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
