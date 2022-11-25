package edu.courses.plannote.service;

import edu.courses.plannote.dto.TransactionDto;
import edu.courses.plannote.entity.*;
import edu.courses.plannote.repository.*;
import edu.courses.plannote.utils.DtoToEntity;
import edu.courses.plannote.utils.EntityToDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImplementation implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImplementation.class);

    private final TransactionRepository transactionRepository;
    public TransactionServiceImplementation(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void addNewTransaction(TransactionDto transaction) {
        TransactionEntity transactionEntity = DtoToEntity.transactionDtoToEntity(transaction);
        transactionRepository.save(transactionEntity);
        log.info("Added new transaction");
    }

    @Override
    public List<TransactionDto> getTransactionsByTaskId(UUID taskId) {
        return transactionRepository.findTransactionEntitiesByTaskTransaction_TaskId(taskId).stream()
                .map(EntityToDto::transactionEntityToDto)
                .toList();
    }
}
