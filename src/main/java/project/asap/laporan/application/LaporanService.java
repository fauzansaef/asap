package project.asap.laporan.application;

import project.asap.laporan.domain.ReportBoxDto;

import java.util.List;

public interface LaporanService {

    List<ReportBoxDto> getReportBoxByKodeBatch(String kodeBatch);

    void generateReportBoxByKodeBatch(String kodeBatch);
}
