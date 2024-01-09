package project.asap.atk.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import project.asap.atk.model.Atks;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface AtksRepository extends JpaRepository<Atks, Integer>, JpaSpecificationExecutor<Atks> {

    Integer countByCreatedAt(LocalDateTime date);

    public static class AtksSpecifications {

        public static Specification<Atks> likeKodeAndNotDeleted(String kode) {
            return (root, query, cb) -> {
                Predicate likeKodePredicate = cb.like(root.get("kode"), "%" + kode + "%");
                Predicate notDeletedPredicate = cb.isNull(root.get("deletedAt"));
                return cb.and(likeKodePredicate, notDeletedPredicate);
            };
        }

        public static Specification<Atks> likeKelompokAndNotDeleted(String nama) {
            return (root, query, cb) -> {
                Predicate likeNamaPredicate = cb.like(root.get("namaAtk"), "%" + nama + "%");
                Predicate notDeletedPredicate = cb.isNull(root.get("deletedAt"));
                return cb.and(likeNamaPredicate, notDeletedPredicate);
            };
        }

        public static Specification<Atks> combinedSpecificationAndNotDeleted(String kode, String nama) {
            return (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (kode != null) {
                    predicates.add(cb.like(root.get("kode"), "%" + kode + "%"));
                }
                if (nama != null) {
                    predicates.add(cb.like(root.get("namaAtk"), "%" + nama + "%"));
                }
                predicates.add(cb.isNull(root.get("deletedAt")));
                return cb.and(predicates.toArray(new Predicate[0]));
            };
        }
    }

}
