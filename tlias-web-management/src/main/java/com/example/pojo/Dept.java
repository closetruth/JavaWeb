package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data                    // 自动生成 getter/setter/toString/equals/hashCode
@NoArgsConstructor       // 自动生成无参构造
@AllArgsConstructor      // 自动生成全参构造
public class Dept {
    private Integer id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
