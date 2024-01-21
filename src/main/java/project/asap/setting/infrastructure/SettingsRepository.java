package project.asap.setting.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.setting.domain.entity.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
}
