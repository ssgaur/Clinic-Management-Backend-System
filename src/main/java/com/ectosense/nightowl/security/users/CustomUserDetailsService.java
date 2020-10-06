package com.ectosense.nightowl.security.users;

import com.ectosense.nightowl.data.entity.User;
import com.ectosense.nightowl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserService userService;

    public CustomUserDetailsService()
    {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userService.getByEmail(username);
        if (null == user)
        {
            throw new UsernameNotFoundException("Can not find User with Email: " + username);
        }
        UserDetails ud =  new CustomUserDetails(user);
        return ud;
    }
}
