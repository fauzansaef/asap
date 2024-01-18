package project.asap.ruangan.application;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asap.exception.ResourceNotFoundException;
import project.asap.files.FilesService;
import project.asap.ruangan.domain.dto.RoomRequest;
import project.asap.ruangan.domain.entity.Rooms;
import project.asap.ruangan.infrastructure.RoomsRepository;
import project.asap.utility.MessageResponse;

@Service
@Transactional
public class RoomsServiceImpl implements RoomsService {
    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(RoomsServiceImpl.class);
    private final RoomsRepository roomsRepository;
    private final FilesService filesService;

    @Autowired
    public RoomsServiceImpl(RoomsRepository roomsRepository, FilesService filesService) {
        this.roomsRepository = roomsRepository;
        this.filesService = filesService;
    }

    @Override
    public Page<Rooms> getAll(int page, int size, String sort, String order, String search) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        Specification<Rooms> specification = (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.isTrue(criteriaBuilder.literal(search.equals(""))),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("kode")), "%" + search.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("namaRuangan")), "%" + search.toLowerCase() + "%")
        );
        return roomsRepository.findAll(specification, pageable);
    }

    @Override
    public Rooms getById(Long id) {
        return roomsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Rooms.class, "id", id.toString()));
    }

    @Override
    public MessageResponse save(RoomRequest roomRequest) {
        try {
            Rooms rooms = new Rooms();
            rooms.setKode(roomRequest.getKode());
            rooms.setNamaRuangan(roomRequest.getNamaRuangan());
            rooms.setDeskripsi(roomRequest.getDeskripsi());
            rooms.setPhoto(roomRequest.getPhoto());
            rooms.setReady(1);
            roomsRepository.save(rooms);
            logger.info("room created");
            return new MessageResponse("room created", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("failed to create room", e);
            return new MessageResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MessageResponse update(Long id, RoomRequest roomRequest) {
        try {
            Rooms rooms = getById(id);
            rooms.setKode(roomRequest.getKode());
            rooms.setNamaRuangan(roomRequest.getNamaRuangan());
            rooms.setDeskripsi(roomRequest.getDeskripsi());

            if (rooms.getPhoto() != null) {
                filesService.delete(rooms.getPhoto());
                logger.info("delete old photo room");
            }

            rooms.setPhoto(roomRequest.getPhoto());
            roomsRepository.save(rooms);
            logger.info("room updated");
            return new MessageResponse("room updated", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("failed to update room", e);
            return new MessageResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MessageResponse delete(Long id) {
        if (roomsRepository.existsById(id)) {
            roomsRepository.deleteById(id);
            if (getById(id).getPhoto() != null) {
                filesService.delete(getById(id).getPhoto());
                logger.info("delete photo room");
            }
            logger.info("room deleted");
            return new MessageResponse("room deleted", HttpStatus.OK);
        } else {
            logger.error("failed to delete room");
            return new MessageResponse("failed to delete room", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
