package com.taylorzhou.community.controller;

import com.taylorzhou.community.dto.AccessTokenDTO;
import com.taylorzhou.community.dto.GitHubUserDTO;
import com.taylorzhou.community.mapper.UserMapper;
import com.taylorzhou.community.model.User;
import com.taylorzhou.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

//引用用户资源文件需要增加注解@PropertySource(value = "classpath:用户资源文件名")
@PropertySource(value = "classpath:github.properties")
@Controller

public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    //引用用户资源文件的配置项需要增加注解@Value("${用户资源文件名.参数名}")
    @Autowired
    private UserMapper userMapper;
    @Value("${github.client_id}")
    private String client_id;
    @Value("${github.client_secret}")
    private String client_secret;
    @Value("${github.redirect_url}")
    private String redirect_url;
    /**
    * @description 回调
    * @param code 登录code
    * @param state 登录状态
    * @param request httpServletRequest
    * @return java.lang.String
    **/
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_url);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GitHubUserDTO githubuser = githubProvider.getGitHubUser(accessToken);
        if (githubuser !=null){
            User user = new User();
            user.setAccount_id(String.valueOf(githubuser.getId()));
            user.setName(githubuser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
            //将获得的user对象放在session里传回给页面
            request.getSession().setAttribute("user",githubuser);
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }
}
