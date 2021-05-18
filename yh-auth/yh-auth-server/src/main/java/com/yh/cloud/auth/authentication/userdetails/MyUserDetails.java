package com.yh.cloud.auth.authentication.userdetails;

import com.yh.common.web.model.ICurrentUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 自定义身份认证对象，实现UserDetails
 *
 * @author yanghan
 * @date 2020/5/29
 */
@NoArgsConstructor
@Data
public class MyUserDetails implements UserDetails {

    private static final long serialVersionUID = 4962121675615445357L;
    /** 用户ID */
    private String userId;
    /** 用户名 */
    private String username;
    /** 昵称 */
    private String nickname;
    /** 用户密码 */
    private String password;
    /** 用户状态 */
    private boolean enabled;
    /** 权限集合 */
    private Collection<GrantedAuthority> authorities;

    public MyUserDetails(ICurrentUser user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
