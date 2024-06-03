package com.apple.shoppingmallboot;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Entity
@ToString
@EqualsAndHashCode
@Getter
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;
    @Column(nullable = false, columnDefinition = "TEXT") private String title;
    @Column(nullable = false) private String date;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
