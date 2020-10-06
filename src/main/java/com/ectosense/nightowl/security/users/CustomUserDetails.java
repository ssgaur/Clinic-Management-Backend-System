package com.ectosense.nightowl.security.users;

import com.ectosense.nightowl.data.entity.User;
import com.ectosense.nightowl.data.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class CustomUserDetails implements UserDetails
{
    private User user;
    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user)
    {
        this.user = user;
        this.username = user.getEmail();
        this.password = user.getPassword();

        List<GrantedAuthority> auths = new ArrayList<>();

        List<UserRole> roleList = user.getUserMeta().getUserRoles();
        for (UserRole role: roleList)
        {
            auths.add(new SimpleGrantedAuthority(role.getValue()));
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
