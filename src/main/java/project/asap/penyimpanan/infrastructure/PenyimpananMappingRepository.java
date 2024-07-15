package project.asap.penyimpanan.infrastructure;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.asap.laporan.domain.ReportBoxDto;
import project.asap.penyimpanan.domain.entity.PenyimpananMapping;

import java.util.List;
import java.util.Optional;

public interface PenyimpananMappingRepository extends JpaRepository<PenyimpananMapping, Long> {
    Optional<PenyimpananMapping> findByIdArsip(Long idArsip);
    Optional<PenyimpananMapping> findByIdBmn(Long idBmn);
    Optional<PenyimpananMapping> findByIdAtk(Long idAtk);
    @Query(value = "select pm.id, " +
            " case " +
            " when pm.id_bmn is not null then b.nama_bmn  " +
            " when pm.id_atk is not null then a.nama_atk  " +
            " when pm.id_arsip is not null then a2.nama  " +
            " end as nama, " +
            " case " +
            " when pm.id_bmn is not null then b.kode " +
            " when pm.id_atk is not null then a.kode  " +
            " when pm.id_arsip is not null then a2.kode  " +
            " end as no_aset, " +
            " case " +
            " when pm.id_bmn is not null then b.kode_lokasi  " +
            " when pm.id_atk is not null then a.kode_lokasi  " +
            " when pm.id_arsip is not null then a2.kode_lokasi  " +
            " end as kode_batch, " +
            " case " +
            " when pm.id_bmn is not null then b.tahun  " +
            " when pm.id_atk is not null then a.tahun  " +
            " when pm.id_arsip is not null then a2.tahun " +
            " end as tahun, " +
            " case " +
            " when pm.id_bmn is not null then b.stock  " +
            " when pm.id_atk is not null then a.stock  " +
            " when pm.id_arsip is not null then a2.jumlah_lembar  " +
            " end as jumlah " +
            " from penyimpanan_mapping pm  " +
            " left join atks a on pm.id_atk = a.id  " +
            " left join bmns b on pm.id_bmn = b.id  " +
            " left join arsip a2 on pm.id_arsip = a2.id  " +
            " where pm.kode_batch = ?1 ", nativeQuery = true)
    List<ReportBoxDto> getReportBoxByKodeBatch(String kodeBatch);

}
