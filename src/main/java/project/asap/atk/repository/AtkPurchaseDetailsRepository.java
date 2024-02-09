package project.asap.atk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.atk.model.AtkPurchaseDetails;

import java.util.List;

public interface AtkPurchaseDetailsRepository extends JpaRepository<AtkPurchaseDetails, Integer> {
    List<AtkPurchaseDetails> findByAtkPurchaseId(Integer atkPurchaseId);
}
