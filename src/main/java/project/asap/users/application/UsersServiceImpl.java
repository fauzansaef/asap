package project.asap.users.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asap.exception.ResourceNotFoundException;
import project.asap.users.domain.dto.UserRequest;
import project.asap.users.domain.entity.Users;
import project.asap.users.infrastructure.UsersRepository;
import project.asap.utility.MessageResponse;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<Users> getAll(int page, int size, String sort, String order, String search) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        Specification<Users> specification = (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.isTrue(criteriaBuilder.literal(search.equals(""))),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + search.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + search.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("ip")), "%" + search.toLowerCase() + "%")
        );
        return usersRepository.findAll(specification, pageable);
    }

    @Override
    public Users getById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Users.class, "id", id.toString()));
    }

    @Override
    public MessageResponse save(UserRequest userRequest) {
        Users users = new Users();
        users.setName(userRequest.getIpPegawai());
        users.setEmail(users.getEmail());
        users.setIp(users.getIp());
        users.setRole(users.getRole());
        usersRepository.save(users);
        return new MessageResponse("success", HttpStatus.OK);
    }

    @Override
    public MessageResponse update(Long id, UserRequest userRequest) {
        Users users = getById(id);
        users.setName(userRequest.getName());
        users.setIp(userRequest.getIpPegawai());
        users.setRole(userRequest.getRole());
        users.setPhoneNumber(userRequest.getPhoneNumber());
        users.setPhoto(userRequest.getPhoto());
        usersRepository.save(users);
        return new MessageResponse("success", HttpStatus.OK);
    }

    @Override
    public MessageResponse delete(Long id) {
        if (usersRepository.existsById(id)) {
            usersRepository.deleteById(id);
            return new MessageResponse("success", HttpStatus.OK);
        } else {
            return new MessageResponse("failed", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public MessageResponse resetPassword(Long id) {
        Users users = getById(id);
        users.setPassword(passwordEncoder.encode(users.getIp()));
        usersRepository.save(users);
        return new MessageResponse("success", HttpStatus.OK);
    }
}
