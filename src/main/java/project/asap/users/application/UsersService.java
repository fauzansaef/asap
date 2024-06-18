package project.asap.users.application;

import org.springframework.data.domain.Page;
import project.asap.users.domain.dto.UserRequest;
import project.asap.users.domain.entity.Users;
import project.asap.utility.MessageResponse;

public interface UsersService {
    Page<Users> getAll(int page, int size, String sort, String order, String search);

    Users getById(Long id);

    MessageResponse save(UserRequest userRequest);

    MessageResponse update(Long id, UserRequest userRequest);

    MessageResponse delete(Long id);

    MessageResponse resetPassword(Long id);

}
