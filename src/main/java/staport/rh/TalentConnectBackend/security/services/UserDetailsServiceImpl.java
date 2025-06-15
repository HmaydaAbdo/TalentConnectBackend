package staport.rh.TalentConnectBackend.security.services;



import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import staport.rh.TalentConnectBackend.security.entities.User;
import staport.rh.TalentConnectBackend.security.repositories.UserRepository;


import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.isEnabled(),
                true, true, true,
                appUser.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                        .collect(Collectors.toList())
        );
    }
}
