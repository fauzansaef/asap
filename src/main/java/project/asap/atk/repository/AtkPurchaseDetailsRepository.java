package project.asap.atk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.atk.model.AtkPurchaseDetails;

public interface AtkPurchaseDetailsRepository extends JpaRepository<AtkPurchaseDetails, Integer> {
}
