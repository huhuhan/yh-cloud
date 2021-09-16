package com.yh.cloud.auth.authentication.userdetails;

import cn.hutool.core.collection.CollUtil;
import com.yh.cloud.auth.model.domain.SysUser;
import com.yh.common.auth.constant.TokenMessageConstant;
import com.yh.common.web.model.ICurrentUser;
import com.yh.common.web.service.ICurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 重写加载身份认证对象，实现UserDetailsService接口或UserDetailsManager接口
 *
 * @author yanghan
 * @date 2020/5/29
 */
@Component
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private List<SysUser> userList;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ICurrentUserService iCurrentUserService;

    @PostConstruct
    public void initData() {
        String password = passwordEncoder.encode("111111");
        userList = new ArrayList<>();
        userList.add(new SysUser("u1", "yh", "测试", true, password, "11111"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = this.getMyUserDetails(username);
        // 验证
        if (!userDetails.isEnabled()) {
            throw new DisabledException(TokenMessageConstant.ACCOUNT_DISABLED);
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException(TokenMessageConstant.ACCOUNT_LOCKED);
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException(TokenMessageConstant.ACCOUNT_EXPIRED);
        } else if (!userDetails.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(TokenMessageConstant.CREDENTIALS_EXPIRED);
        }
        return userDetails;
    }

    private MyUserDetails getMyUserDetails(String username) {
        // todo: 扩展，缓存存储、读取

        ICurrentUser iCurrentUser = iCurrentUserService.getByUserName(username);
        if (null == iCurrentUser || null == iCurrentUser.getUserId()) {
            // todo：测试用户
            iCurrentUser = this.getTempUser(username);
            if (null == iCurrentUser) {
                throw new UsernameNotFoundException(TokenMessageConstant.USERNAME_PASSWORD_ERROR);
            }
        }
        MyUserDetails myUserDetails = new MyUserDetails(iCurrentUser);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        //todo: 数据库查询用户角色，遍历存到collection中

        // todo: 当前系统暂不鉴权
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        myUserDetails.setAuthorities(authorities);

        return myUserDetails;
    }


    private ICurrentUser getTempUser(String username) {
        List<SysUser> findUserList = userList.stream().filter(item -> item.getUsername().equals(username)).collect(Collectors.toList());
        if (CollUtil.isEmpty(findUserList)) {
            throw new UsernameNotFoundException(TokenMessageConstant.USERNAME_PASSWORD_ERROR);
        }
        return findUserList.get(0);
    }
}
