package edu.cources.plannote.repository;

import edu.cources.plannote.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    @Query(value = "select transaction from TransactionEntity transaction where transaction.taskTransaction.taskId = :taskId")
    List<TransactionEntity> getTransactionsByTaskId(@Param("taskId") UUID taskId);

    @Query(value = "select sum(transactions.transactionMoneyFlow) from TransactionEntity transactions where transactions.taskTransaction.taskId = :taskId")
    List<TransactionEntity> getMoneyFlowSumByTaskId(@Param("taskId") UUID taskId);
}
