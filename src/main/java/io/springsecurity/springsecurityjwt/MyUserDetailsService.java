package io.springsecurity.springsecurityjwt;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

//        User user = userDao.findByUsername(username);
//        List<SimpleGrantedAuthority> grantedAuthorities = user.getAuthorities()
//                .stream().map(authority -> new SimpleGrantedAuthority(authority.toString()))
//                .collect(Collectors.toList());
//        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("FIRST");
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        updatedAuthorities.add(authority);

        //Return an hardcoded user
        return new User("foo","foo",
                updatedAuthorities);
    }

    public UserDetails loadUserByUserId(String user_id) {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("FIRST");
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        updatedAuthorities.add(authority);

        return new User("10","foo",
                updatedAuthorities);
    }
}
