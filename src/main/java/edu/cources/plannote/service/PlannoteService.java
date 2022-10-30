package edu.cources.plannote.service;

import edu.cources.plannote.entity.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface PlannoteService {
    List<AccountStatusEntity> accountStatusList();

    List<LabelEntity> labelList();

    void addNewLabel(LabelEntity label);

    List<PriorityEntity> priorityList();

    List<ScoreEntity> scoreList();

    List<StatusEntity> statusList();

    List<TransactionEntity> transactionList();

    void addNewTransaction(TransactionEntity transaction);

    void deleteTransaction(TransactionEntity transaction);

    void changeTransaction(TransactionEntity transaction);

    List<UserStatusEntity> userStatusList();
}
