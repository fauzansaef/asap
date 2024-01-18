package project.asap.bmn.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.asap.bmn.domain.dto.BmnRequest;
import project.asap.bmn.domain.entity.Bmns;
import project.asap.utility.MessageResponse;

@RestController
@RequestMapping("/api/v1/bmns")
@Tag(name = "BMN (Barang Milik Negara)", description = "API Transaction Barang Milik Negara")
public class BmnController {
    private final BmnService bmnService;

    @Autowired
    public BmnController(BmnService bmnService) {
        this.bmnService = bmnService;
    }

    @GetMapping("")
    ResponseEntity<Page<Bmns>> getAll(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sort,
                                      @RequestParam(defaultValue = "asc") String order,
                                      @RequestParam(defaultValue = "") String search) {
        return new ResponseEntity<>(bmnService.getAll(page, size, sort, order, search), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Bmns> getById(@RequestParam Long id) {
        return new ResponseEntity<>(bmnService.getById(id), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<MessageResponse> save(@RequestBody BmnRequest bmnRequest) {
        return new ResponseEntity<>(bmnService.save(bmnRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody BmnRequest bmnRequest) {
        return new ResponseEntity<>(bmnService.update(id, bmnRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        return new ResponseEntity<>(bmnService.delete(id), HttpStatus.OK);
    }
}
