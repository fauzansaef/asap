package project.asap.setting.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.asap.setting.domain.dto.SlideRequest;
import project.asap.setting.domain.dto.TextRequest;

@RestController
@RequestMapping("/api/v1/settings")
@Tag(name = "Setting", description = "API Transaction Setting")
public class SettingController {
    private final SettingService settingService;

    @Autowired
    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping("")
    public ResponseEntity<?> getSettings() {
        return new ResponseEntity<>(settingService.getSettings(), HttpStatus.OK);
    }

    @PatchMapping("/slide")
    public ResponseEntity<?> updateSlide(@RequestBody SlideRequest slideRequest) {
        return new ResponseEntity<>(settingService.updateSlide(slideRequest), HttpStatus.OK);
    }

    @DeleteMapping("/slide")
    public ResponseEntity<?> deleteSlide(@RequestBody SlideRequest slideRequest) {
        return new ResponseEntity<>(settingService.deleteSlide(slideRequest), HttpStatus.OK);
    }

    @PatchMapping("/text")
    public ResponseEntity<?> updateText(@RequestBody TextRequest textRequest) {
        return new ResponseEntity<>(settingService.updateText(textRequest), HttpStatus.OK);
    }

    @PatchMapping("/kasubbag")
    public ResponseEntity<?> updateKasubbag(@RequestParam Long idUsers) {
        return new ResponseEntity<>(settingService.updateKasubbag(idUsers), HttpStatus.OK);
    }


}
