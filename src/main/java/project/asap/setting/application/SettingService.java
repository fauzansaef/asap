package project.asap.setting.application;

import project.asap.setting.domain.dto.SlideRequest;
import project.asap.setting.domain.dto.TextRequest;
import project.asap.setting.domain.entity.Settings;
import project.asap.utility.MessageResponse;

public interface SettingService {
    MessageResponse updateSlide(SlideRequest slideRequest);
    MessageResponse deleteSlide(SlideRequest slideRequest);
    MessageResponse updateText(TextRequest textRequest);
    MessageResponse updateKasubbag(Long idUsers);
    Settings getSettings();
}
