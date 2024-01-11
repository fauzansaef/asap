package project.asap.ruangan.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.asap.ruangan.domain.dto.RoomRequest;
import project.asap.ruangan.domain.entity.Rooms;
import project.asap.utility.MessageResponse;

@RestController
@RequestMapping("/api/v1/rooms")
@Tag(name = "Rooms", description = "API Transaction room")
public class RoomsController {
    private final RoomsService roomsService;

    @Autowired
    public RoomsController(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @GetMapping("")
    ResponseEntity<Page<Rooms>> getAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sort,
                                       @RequestParam(defaultValue = "asc") String order,
                                       @RequestParam(defaultValue = "") String search) {
        return new ResponseEntity<>(roomsService.getAll(page, size, sort, order, search), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Rooms> getById(@RequestParam Long id) {
        return new ResponseEntity<>(roomsService.getById(id), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<MessageResponse> save(@RequestBody RoomRequest roomRequest) {
        return new ResponseEntity<>(roomsService.save(roomRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody RoomRequest roomRequest) {
        return new ResponseEntity<>(roomsService.update(id, roomRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        return new ResponseEntity<>(roomsService.delete(id), HttpStatus.OK);
    }
}
