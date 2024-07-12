package project.asap.laporan.application;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/laporan")
@Tag(name = "Laporan", description = "API get data for Laporan")
public class LaporanController {
}
