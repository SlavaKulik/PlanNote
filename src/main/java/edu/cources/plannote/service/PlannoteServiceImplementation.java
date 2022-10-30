package edu.cources.plannote.service;

import edu.cources.plannote.entity.*;
import edu.cources.plannote.repository.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PlannoteServiceImplementation implements PlannoteService{

    private final AccountStatusRepository accountStatusRepository;
    private final LabelRepository labelRepository;
    private final PriorityRepository priorityRepository;
    private final ScoreRepository scoreRepository;
    private final StatusRepository statusRepository;
    private final TransactionRepository transactionRepository;
    private final UserStatusRepository userStatusRepository;

    public PlannoteServiceImplementation(AccountStatusRepository accountStatusRepository,
                                         LabelRepository labelRepository,
                                         PriorityRepository priorityRepository,
                                         ScoreRepository scoreRepository,
                                         StatusRepository statusRepository,
                                         TransactionRepository transactionRepository,
                                         UserStatusRepository userStatusRepository) {
        this.accountStatusRepository = accountStatusRepository;
        this.labelRepository = labelRepository;
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
    public List<LabelEntity> labelList() {
        return labelRepository.findAll();
    }

    @Override
    public void addNewLabel(LabelEntity label) { labelRepository.save(label); }

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
    public void addNewTransaction(TransactionEntity transaction) { transactionRepository.save(transaction); }

    @Override
    public void deleteTransaction(TransactionEntity transaction) { transactionRepository.delete(transaction); }

    @Override
    public void changeTransaction(TransactionEntity transaction) {  }

    @Override
    public List<UserStatusEntity> userStatusList() {
        return userStatusRepository.findAll();
    }
}
