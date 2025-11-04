package com.footlink.footlink.search.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor          // MyBatis 빈 생성자 필요
@AllArgsConstructor
public class SuggestItem {
    private String id;
    private String name;
}
