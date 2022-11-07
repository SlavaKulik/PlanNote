package edu.cources.plannote.service;

import edu.cources.plannote.dto.TransactionDto;
import edu.cources.plannote.entity.*;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface PlannoteService {
    List<AccountStatusEntity> accountStatusList();

    List<PriorityEntity> priorityList();

    List<ScoreEntity> scoreList();

    List<StatusEntity> statusList();

    List<TransactionEntity> transactionList();

    void addNewTransaction(TransactionDto transaction);

    List<TransactionDto> getTransactionsByTaskId(UUID taskId);

    List<TransactionDto> getMoneyFlowSumByTaskId(UUID taskId);

    void deleteTransaction(TransactionEntity transaction);

    void changeTransaction(TransactionEntity transaction);

    List<UserStatusEntity> userStatusList();
}
