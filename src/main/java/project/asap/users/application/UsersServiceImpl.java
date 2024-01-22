package project.asap.users.application;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
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
import project.asap.files.FilesService;
import project.asap.users.domain.dto.UserRequest;
import project.asap.users.domain.entity.Users;
import project.asap.users.infrastructure.UsersRepository;
import project.asap.utility.MessageResponse;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(UsersServiceImpl.class);
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final FilesService filesService;


    /**
     * role users => 1 = kasubbag, 2 = pelaksana 3 = PIC
     */

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, FilesService filesService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.filesService = filesService;
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
        try {
            Users users = new Users();
            users.setName(userRequest.getIpPegawai());
            users.setEmail(userRequest.getEmail());
            users.setIp(userRequest.getIpPegawai());
            users.setRole(userRequest.getRole());
            users.setPassword(passwordEncoder.encode(userRequest.getIpPegawai()));
            users.setSectionId(userRequest.getSectionId());
            users.setSubSectionId(userRequest.getSubSectionId());
            usersRepository.save(users);
            logger.info("user created");
            return new MessageResponse("success", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("failed to create user", e);
            return new MessageResponse("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public MessageResponse update(Long id, UserRequest userRequest) {
        try {
            Users users = getById(id);
            users.setName(userRequest.getName());
            users.setRole(userRequest.getRole());
            users.setPhoneNumber(userRequest.getPhoneNumber());
            users.setSectionId(userRequest.getSectionId());
            users.setSubSectionId(userRequest.getSubSectionId());

            if (users.getPhoto() != null) {
                filesService.delete(users.getPhoto());
                logger.info("delete old photo user");
            }

            users.setPhoto(userRequest.getPhoto());
            usersRepository.save(users);
            logger.info("user updated");
            return new MessageResponse("success", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("failed to update user", e);
            return new MessageResponse("failed", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public MessageResponse delete(Long id) {
        if (usersRepository.existsById(id)) {
            usersRepository.deleteById(id);


            if (getById(id).getPhoto() != null) {
                filesService.delete(getById(id).getPhoto());
                logger.info("delete photo user");
            }

            logger.info("user deleted");
            return new MessageResponse("success", HttpStatus.OK);
        } else {
            logger.error("failed to delete user");
            return new MessageResponse("failed", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public MessageResponse resetPassword(Long id) {
        try {
            Users users = getById(id);
            users.setPassword(passwordEncoder.encode(users.getIp()));
            usersRepository.save(users);
            logger.info("password reset");
            return new MessageResponse("success", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("failed to reset password", e);
            return new MessageResponse("failed", HttpStatus.BAD_REQUEST);
        }

    }
}
