package com.garm.security.service.userdetail;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // find your user from database

        /*if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("security.bad.credencial");
        } else if (!user.isEnabled()) {
            throw new CredentialException("security.bad.user.disabled");
        }*/

        // return new User(user.getUserName(), user.getPassword(), getAuthority());
        return null;
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

}