package project.asap.ref.application.reftipearsip;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.asap.ref.domain.dto.RefTipeArsipRequest;
import project.asap.ref.domain.entity.RefTipeArsip;
import project.asap.utility.MessageResponse;

@RestController
@RequestMapping("/api/v1/ref-tipe-arsip")
@Tag(name = "Ref Tipe Arsip", description = "API Transaction Referensi Tipe Arsip")
public class RefTipeArsipController {
    private final RefTipeArsipService refTipeArsipService;

    @Autowired
    public RefTipeArsipController(RefTipeArsipService refTipeArsipService) {
        this.refTipeArsipService = refTipeArsipService;
    }

    @GetMapping("")
    ResponseEntity<Page<RefTipeArsip>> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "id") String sort,
                                              @RequestParam(defaultValue = "asc") String order,
                                              @RequestParam(defaultValue = "") String search) {
        return new ResponseEntity<>(refTipeArsipService.getAll(page, size, sort, order, search), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<RefTipeArsip> getById(@RequestParam Long id) {
        return new ResponseEntity<>(refTipeArsipService.getById(id), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<MessageResponse> save(@RequestBody RefTipeArsipRequest refTipeArsipRequest) {
        return new ResponseEntity<>(refTipeArsipService.save(refTipeArsipRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody RefTipeArsipRequest refTipeArsipRequest) {
        return new ResponseEntity<>(refTipeArsipService.update(id, refTipeArsipRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        return new ResponseEntity<>(refTipeArsipService.delete(id), HttpStatus.OK);
    }
}
