package edu.courses.plannote.repository;

import edu.courses.plannote.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findTransactionEntitiesByTaskTransaction_TaskId(UUID id);
}
