package com.taylorzhou.community.dto;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

@Setter
@Getter
public class GitHubUserDTO {
    private String name;
    private Long id;
    private String bio;
}
