package app.MyBackendApp.main.bll.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import app.MyBackendApp.main.dal.UserInfoRepository;
import app.MyBackendApp.main.domain.entities.Client;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserInfoRepository userinfoRepository;

    public UserDetailsServiceImpl(UserInfoRepository userinfoRepository) {
        this.userinfoRepository = userinfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
    Client user = userinfoRepository.findByUsername(mail);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with mail: " + mail);
        }
        return user;
    }
}
