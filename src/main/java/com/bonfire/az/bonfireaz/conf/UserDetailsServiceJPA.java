package com.bonfire.az.bonfireaz.conf;

import com.bonfire.az.bonfireaz.model.entity.XUser;
import com.bonfire.az.bonfireaz.model.sec.XUserDetails;
import com.bonfire.az.bonfireaz.repo.UserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j2
@Configuration
public class UserDetailsServiceJPA implements UserDetailsService {

    private final UserRepo repo;

    public UserDetailsServiceJPA(UserRepo repo) {
        this.repo = repo;
    }

    public static UserDetails mapper_to_xUserDetails(XUser xuser) {
        return new XUserDetails(
                xuser.getId(),
                xuser.getUserId(),
                xuser.getEmail(),
                xuser.getPassword(),
                xuser.getRoles()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info(String.format(">>>> loading user details for user: %s", email));
        return repo.findByEmail(email)
                .map(UserDetailsServiceJPA::mapper_to_xUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User: %s isn't found in our DB", email)
                ));
    }

    public UserDetails loadUserByUserId(long id) throws UsernameNotFoundException {
        log.info(String.format(">>>> loading user details for user(id): %s", id));
        return repo.findById(id)
                .map(UserDetailsServiceJPA::mapper_to_xUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User_id: %d isn't found in our DB", id)
                ));
    }

}
