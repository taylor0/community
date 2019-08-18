package com.taylorzhou.community.controller;

import com.taylorzhou.community.dto.AccessTokenDTO;
import com.taylorzhou.community.dto.GitHubUserDTO;
import com.taylorzhou.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@PropertySource(value = "classpath:github.properties")
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client_id}")
    private String client_id;
    @Value("${github.client_secret}")
    private String client_secret;
    @Value("${github.redirect_url}")
    private String redirect_url;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_url);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GitHubUserDTO gitHubUserDTO = githubProvider.getGitHubUser(accessToken);
        System.out.println(gitHubUserDTO.getName());
        return "index";
    }
}
