package com.taylorzhou.community.provider;

import com.alibaba.fastjson.JSON;
import com.taylorzhou.community.dto.AccessTokenDTO;
import com.taylorzhou.community.dto.GitHubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
使用@Component注解后该类会被sprintBoot自动加载到容器中，只需要在使用的时候
1、申明私有化类变量
2、在变量上一行添加@Autowired注解
 */
@Component

public class GithubProvider {
    /**
    * @description 获取登录令牌
    * @param accessTokenDTO
    * @return java.lang.String
    **/
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //返回的string为access_token=6888d51da130439e8edc4c4c3faf1482365b2b48&scope=user&token_type=bearer
            String string = response.body().string();
            //先以&分割取第一段字符串，再以=分割取第二段字符
            String accessToken = string.split("&")[0].split("=")[1];
            //System.out.println("accessToken:" + accessToken);
            return accessToken;
        } catch (IOException e) {

        }
        return null;
    }
    /**
    * @description 获取github用户信息
    * @param accessToken
    * @return com.taylorzhou.community.dto.GitHubUserDTO
    **/
    public GitHubUserDTO getGitHubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //parseObject自动将String的json对象转换成java的类对象
            GitHubUserDTO gitHubUserDTO = JSON.parseObject(string, GitHubUserDTO.class);
            return gitHubUserDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
