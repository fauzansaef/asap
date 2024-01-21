package project.asap.setting.application;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asap.exception.CustomValidationException;
import project.asap.files.FilesService;
import project.asap.setting.domain.dto.SlideRequest;
import project.asap.setting.domain.dto.TextRequest;
import project.asap.setting.domain.entity.Settings;
import project.asap.setting.infrastructure.SettingsRepository;
import project.asap.users.application.UsersService;
import project.asap.users.infrastructure.UsersRepository;
import project.asap.utility.MessageResponse;

@Service
@Transactional
public class SettingServiceImpl implements SettingService {
    private static final Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(SettingServiceImpl.class);
    private final SettingsRepository settingsRepository;
    private final UsersService usersService;
    private final FilesService filesService;

    @Autowired
    public SettingServiceImpl(SettingsRepository settingsRepository, UsersService usersService, FilesService filesService) {
        this.settingsRepository = settingsRepository;
        this.usersService = usersService;
        this.filesService = filesService;
    }

    @Override
    public MessageResponse updateSlide(SlideRequest slideRequest) {
        Settings settings = getSettings();

        switch (slideRequest.getJenisSlide()) {
            case 1:
                settings.setSlide1(slideRequest.getFileSlide());
                break;
            case 2:
                settings.setSlide2(slideRequest.getFileSlide());
                break;
            case 3:
                settings.setSlide3(slideRequest.getFileSlide());
                break;
            case 4:
                settings.setSlide4(slideRequest.getFileSlide());
                break;
            case 5:
                settings.setSlide5(slideRequest.getFileSlide());
                break;
            case 6:
                settings.setSlide6(slideRequest.getFileSlide());
                break;
            default:
                throw new CustomValidationException(HttpStatus.BAD_REQUEST, "jenis slide tidak ditemukan");
        }
        settingsRepository.save(settings);
        logger.info("slide updated");
        return new MessageResponse("slide updated", HttpStatus.OK);
    }

    @Override
    public MessageResponse deleteSlide(SlideRequest slideRequest) {
        Settings settings = getSettings();
        switch (slideRequest.getJenisSlide()) {
            case 1:
                filesService.delete(slideRequest.getFileSlide());
                settings.setSlide1(null);
                break;
            case 2:
                filesService.delete(slideRequest.getFileSlide());
                settings.setSlide2(null);
                break;
            case 3:
                filesService.delete(slideRequest.getFileSlide());
                settings.setSlide3(null);
                break;
            case 4:
                filesService.delete(slideRequest.getFileSlide());
                settings.setSlide4(null);
                break;
            case 5:
                filesService.delete(slideRequest.getFileSlide());
                settings.setSlide5(null);
                break;
            case 6:
                filesService.delete(slideRequest.getFileSlide());
                settings.setSlide6(null);
                break;
            default:
                throw new CustomValidationException(HttpStatus.BAD_REQUEST, "jenis slide tidak ditemukan");
        }
        settingsRepository.save(settings);
        logger.info("slide deleted");
        return new MessageResponse("slide deleted", HttpStatus.OK);
    }

    @Override
    public MessageResponse updateText(TextRequest textRequest) {
        Settings settings = getSettings();
        switch (textRequest.getJenisText()) {
            case 1:
                settings.setText1(textRequest.getText());
                break;
            case 2:
                settings.setText2(textRequest.getText());
                break;
            case 3:
                settings.setText3(textRequest.getText());
                break;
            case 4:
                settings.setText4(textRequest.getText());
                break;
            case 5:
                settings.setText5(textRequest.getText());
                break;
            case 6:
                settings.setText6(textRequest.getText());
                break;
            default:
                throw new CustomValidationException(HttpStatus.BAD_REQUEST, "jenis text tidak ditemukan");
        }
        settingsRepository.save(settings);
        logger.info("text updated");
        return new MessageResponse("text updated", HttpStatus.OK);
    }

    @Override
    public MessageResponse updateKasubbag(Long idUsers) {
        Settings settings = getSettings();
        settings.setKasubagId(usersService.getById(idUsers).getId());
        settingsRepository.save(settings);
        logger.info("kasubbag updated");
        return new MessageResponse("kasubbag updated", HttpStatus.OK);
    }

    @Override
    public Settings getSettings() {
        return settingsRepository.findById(1L).orElse(null);
    }
}
