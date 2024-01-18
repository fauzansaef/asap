package project.asap.kdo.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.asap.kdo.domain.dto.KdoRequest;
import project.asap.kdo.domain.entity.Kdos;
import project.asap.utility.MessageResponse;

@RestController
@RequestMapping("/api/v1/kdos")
@Tag(name = "KDO (Kendaraan Dinas Operasional)", description = "API Transaction Kendaraan Dinas Operasional")
public class KdoController {
    private final KdoService kdoService;

    @Autowired
    public KdoController(KdoService kdoService) {
        this.kdoService = kdoService;
    }

    @GetMapping("")
    ResponseEntity<Page<Kdos>> getAll(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sort,
                                      @RequestParam(defaultValue = "asc") String order,
                                      @RequestParam(defaultValue = "") String search) {
        return new ResponseEntity<>(kdoService.getAll(page, size, sort, order, search), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Kdos> getById(@RequestParam Long id) {
        return new ResponseEntity<>(kdoService.getById(id), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<MessageResponse> save(@RequestBody KdoRequest kdoRequest) {
        return new ResponseEntity<>(kdoService.save(kdoRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody KdoRequest kdoRequest) {
        return new ResponseEntity<>(kdoService.update(id, kdoRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        return new ResponseEntity<>(kdoService.delete(id), HttpStatus.OK);
    }
}
