package com.yh.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

import java.util.*;

/**
 * 读取客户端配置信息，业务层，可配合数据库
 * @author yanghan
 * @date 2019/7/2
 */
//@Service
public class MyClientDetailService implements ClientDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String s) {
        return new ClientDetails() {
            @Override
            public String getClientId() {
                return "client_demo";
            }

            @Override
            public Set<String> getResourceIds() {
                return null;
            }

            @Override
            public boolean isSecretRequired() {
                return true;
            }

            @Override
            public String getClientSecret() {
//                return "secret_client_demo";
                return passwordEncoder.encode("secret_client_demo");
            }

            @Override
            public boolean isScoped() {
                return true;
            }

            @Override
            public Set<String> getScope() {
                HashSet<String> strings = new HashSet<>();
                strings.add("all");
                return strings;
            }

            @Override
            public Set<String> getAuthorizedGrantTypes() {
                //这里配置支持哪几种授权模式
                Set<String> strings = new HashSet<>();
                strings.add("authorization_code");
                strings.add("refresh_token");
                strings.add("password");
                strings.add("client_credentials");
                return strings;
            }

            @Override
            public Set<String> getRegisteredRedirectUri() {
                Set<String> strings = new HashSet<>();
                strings.add("http://localhost:7003/auth/account");//这里是重定向地址及最后code授权码返回地址
                return strings;
            }

            @Override
            public Collection<GrantedAuthority> getAuthorities() {
                List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
                grantedAuthorityList.add(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "authority_demo";
                    }
                });
                return grantedAuthorityList;
            }

            @Override
            public Integer getAccessTokenValiditySeconds() {
                return 3600;
            }

            @Override
            public Integer getRefreshTokenValiditySeconds() {
                return 3600;
            }

            @Override
            public boolean isAutoApprove(String s) {
                return true;
            }

            @Override
            public Map<String, Object> getAdditionalInformation() {
                return null;
            }
        };
    }
}
