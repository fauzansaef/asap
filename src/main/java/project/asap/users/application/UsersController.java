package project.asap.users.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.asap.users.domain.dto.UserRequest;
import project.asap.users.domain.entity.Users;
import project.asap.utility.MessageResponse;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "API Transaction user")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("")
    ResponseEntity<Page<Users>> getAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sort,
                                       @RequestParam(defaultValue = "asc") String order,
                                       @RequestParam(defaultValue = "") String search) {
        return new ResponseEntity<>(usersService.getAll(page, size, sort, order, search), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Users> getById(@PathVariable Long id) {
        return new ResponseEntity<>(usersService.getById(id), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<MessageResponse> save(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(usersService.save(userRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(usersService.update(id, userRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        return new ResponseEntity<>(usersService.delete(id), HttpStatus.OK);
    }

    @PatchMapping("/reset-password/{id}")
    ResponseEntity<MessageResponse> resetPassword(@PathVariable Long id) {
        return new ResponseEntity<>(usersService.resetPassword(id), HttpStatus.OK);
    }

    @GetMapping("/section")
    ResponseEntity<?> getSection() {
        return new ResponseEntity<>(usersService.getSection(), HttpStatus.OK);
    }

    @GetMapping("/sub-section/{id}")
    ResponseEntity<?> getSubSectionBySectionId(@PathVariable Long id) {
        return new ResponseEntity<>(usersService.getSubSectionBySectionId(id), HttpStatus.OK);
    }

    @GetMapping("/list")
    ResponseEntity<?> getListUsers() {
        return new ResponseEntity<>(usersService.getListUsers(), HttpStatus.OK);
    }
}
