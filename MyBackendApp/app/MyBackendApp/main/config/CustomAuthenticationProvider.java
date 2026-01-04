package app.MyBackendApp.main.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import app.MyBackendApp.main.bll.impl.UserDetailsServiceImpl;
import app.MyBackendApp.main.dal.ClientRepository;
import app.MyBackendApp.main.domain.entities.Client;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    //private final UserDetailsServiceImpl userDetailsService;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsServiceImpl userDetailsService, ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        //this.userDetailsService = userDetailsService;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<Client> userDetails = clientRepository.findClientByUsername(username);
        if(userDetails.isPresent()){
            if(userDetails !=null && passwordEncoder.matches(password, userDetails.get().getPassword())){            
                return new UsernamePasswordAuthenticationToken(userDetails.get(), password, userDetails.get().getAuthorities());
            }else{
                throw new BadCredentialsException("Invalid password");
            }
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

