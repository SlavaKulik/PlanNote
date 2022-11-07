package edu.cources.plannote.service;

import edu.cources.plannote.dto.TransactionDto;
import edu.cources.plannote.entity.*;
import edu.cources.plannote.repository.*;
import edu.cources.plannote.utils.DtoToEntity;
import edu.cources.plannote.utils.EntityToDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PlannoteServiceImplementation implements PlannoteService{

    private final AccountStatusRepository accountStatusRepository;
    private final PriorityRepository priorityRepository;
    private final ScoreRepository scoreRepository;
    private final StatusRepository statusRepository;
    private final TransactionRepository transactionRepository;
    private final UserStatusRepository userStatusRepository;

    public PlannoteServiceImplementation(AccountStatusRepository accountStatusRepository,
                                         PriorityRepository priorityRepository,
                                         ScoreRepository scoreRepository,
                                         StatusRepository statusRepository,
                                         TransactionRepository transactionRepository,
                                         UserStatusRepository userStatusRepository) {
        this.accountStatusRepository = accountStatusRepository;
        this.priorityRepository = priorityRepository;
        this.scoreRepository = scoreRepository;
        this.statusRepository = statusRepository;
        this.transactionRepository = transactionRepository;
        this.userStatusRepository = userStatusRepository;
    }

    @Override
    public List<AccountStatusEntity> accountStatusList() {
        return accountStatusRepository.findAll();
    }

    @Override
    public List<PriorityEntity> priorityList() { return priorityRepository.findAll(); }

    @Override
    public List<ScoreEntity> scoreList() {
        return scoreRepository.findAll();
    }

    @Override
    public List<StatusEntity> statusList() {
        return statusRepository.findAll();
    }

    @Override
    public List<TransactionEntity> transactionList() {
        return transactionRepository.findAll();
    }

    @Override
    public void addNewTransaction(TransactionDto transaction) {
        TransactionEntity transactionEntity = DtoToEntity.transactionDtoToEntity(transaction);
        transactionRepository.save(transactionEntity);
    }

    @Override
    public List<TransactionDto> getTransactionsByTaskId(UUID taskId) {
        return transactionRepository.getTransactionsByTaskId(taskId).stream()
                .map(EntityToDto::transactionEntityToDto)
                .toList();
    }

    @Override
    public List<TransactionDto> getMoneyFlowSumByTaskId(UUID taskId) {
        return transactionRepository.getMoneyFlowSumByTaskId(taskId).stream()
                .map(EntityToDto::transactionEntityToDto)
                .toList();
    }

    @Override
    public void deleteTransaction(TransactionEntity transaction) { transactionRepository.delete(transaction); }

    @Override
    public void changeTransaction(TransactionEntity transaction) {  }

    @Override
    public List<UserStatusEntity> userStatusList() {
        return userStatusRepository.findAll();
    }
}
