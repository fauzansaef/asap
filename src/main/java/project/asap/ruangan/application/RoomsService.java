package project.asap.ruangan.application;

import org.springframework.data.domain.Page;
import project.asap.ruangan.domain.dto.RoomRequest;
import project.asap.ruangan.domain.entity.Rooms;
import project.asap.utility.MessageResponse;

public interface RoomsService {
    Page<Rooms> getAll(int page, int size, String sort, String order, String search);
    Rooms getById(Long id);
    MessageResponse save(RoomRequest roomRequest);
    MessageResponse update(Long id, RoomRequest roomRequest);
    MessageResponse delete(Long id);


}
