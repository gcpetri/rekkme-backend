package com.rekkme.dtos.forms;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RekRequestDto {

    private List<String> usernames = new ArrayList<>();
    private String url;
    private Integer wager;
    private String title;
    private String imageUrl;
    private String description;
    private List<String> tags = new ArrayList<>();
    private String artist = "";
    private String location = "";
    private String category;
}
