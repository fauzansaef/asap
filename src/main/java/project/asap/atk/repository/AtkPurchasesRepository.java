package project.asap.atk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asap.atk.model.AtkPurchases;

public interface AtkPurchasesRepository extends JpaRepository<AtkPurchases, Integer> {
}
