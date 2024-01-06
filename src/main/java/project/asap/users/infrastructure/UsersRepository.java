package project.asap.users.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.users.domain.entity.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByIp(String ip);
}
