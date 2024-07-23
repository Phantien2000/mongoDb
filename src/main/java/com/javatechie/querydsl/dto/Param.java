package com.javatechie.querydsl.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Param {
    private List<String> groups = new ArrayList<>();
    private List<String> selects = new ArrayList<>();
    private List<String> orders = new ArrayList<>();
}
