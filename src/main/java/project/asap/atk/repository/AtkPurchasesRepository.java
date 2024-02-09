package project.asap.atk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.atk.model.AtkPurchases;

import java.util.List;
import java.util.Optional;

public interface AtkPurchasesRepository extends JpaRepository<AtkPurchases, Integer> {
    Optional<AtkPurchases>findByNomorPoAndStatus(String nomorPo, Integer status );

    List<AtkPurchases> findByUserIdAndStatus(Integer userId, Integer status);

}
