package project.asap.security.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asap.security.domain.UserDetailsImpl;
import project.asap.users.domain.entity.Users;
import project.asap.users.infrastructure.UsersRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByIp(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found : " + username));
        return UserDetailsImpl.build(user);
    }
}
