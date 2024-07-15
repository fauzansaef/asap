package project.asap.laporan.application;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.asap.laporan.domain.ReportBoxDto;
import project.asap.penyimpanan.infrastructure.PenyimpananMappingRepository;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class LaporanServiceImpl implements LaporanService{
    private final PenyimpananMappingRepository penyimpananMappingRepository;

    @Autowired
    public LaporanServiceImpl(PenyimpananMappingRepository penyimpananMappingRepository) {
        this.penyimpananMappingRepository = penyimpananMappingRepository;
    }

    @Override
    public List<ReportBoxDto> getReportBoxByKodeBatch(String kodeBatch) {
        return penyimpananMappingRepository.getReportBoxByKodeBatch(kodeBatch);
    }

    @Override
    public void generateReportBoxByKodeBatch(String kodeBatch) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Report.pdf"));
            document.open();

            PdfPTable table = new PdfPTable(6); // 6 columns.
            PdfPCell cell;

            // Set total width and relative widths of columns
            float totalWidth = 1200; // Total width of the table
            float[] columnWidths = {1f, 5f, 3f, 2f, 1f, 1f}; // Relative widths of the columns
            table.setTotalWidth(totalWidth);
            table.setWidths(columnWidths);

            // Adding headers
            String[] headerText = {"No", "Nama", "No Aset", "Kode Batch", "Tahun", "Jumlah"};
            for (String text : headerText) {
                cell = new PdfPCell(new Phrase(text));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            // Adding data
            int serquence = 1;
            for (ReportBoxDto data : penyimpananMappingRepository.getReportBoxByKodeBatch(kodeBatch)) {
                table.addCell(String.valueOf(serquence++));
                table.addCell(data.getNama());
                table.addCell(data.getNo_aset());
                table.addCell(data.getKode_batch());
                table.addCell(data.getTahun());
                table.addCell(data.getJumlah().toString());
            }

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
