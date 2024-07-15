package project.asap.laporan.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.asap.laporan.domain.ReportBoxDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/laporan")
@Tag(name = "Laporan", description = "API get data for Laporan")
public class LaporanController {
    private final LaporanService laporanService;

    @Autowired
    public LaporanController(LaporanService laporanService) {
        this.laporanService = laporanService;
    }

    @GetMapping("/box")
    public List<ReportBoxDto> getReportBoxByKodeBatch(String kodeBatch) {
        return laporanService.getReportBoxByKodeBatch(kodeBatch);
    }
    @GetMapping("/box/generate")
    public void generateReportBoxByKodeBatch(String kodeBatch) {
        laporanService.generateReportBoxByKodeBatch(kodeBatch);
    }
}
