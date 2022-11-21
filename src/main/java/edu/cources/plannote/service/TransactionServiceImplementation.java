package edu.cources.plannote.service;

import edu.cources.plannote.dto.TransactionDto;
import edu.cources.plannote.entity.*;
import edu.cources.plannote.repository.*;
import edu.cources.plannote.utils.DtoToEntity;
import edu.cources.plannote.utils.EntityToDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;
    public TransactionServiceImplementation(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void addNewTransaction(TransactionDto transaction) {
        TransactionEntity transactionEntity = DtoToEntity.transactionDtoToEntity(transaction);
        transactionRepository.save(transactionEntity);
    }

    @Override
    public List<TransactionDto> getTransactionsByTaskId(UUID taskId) {
        return transactionRepository.findTransactionEntitiesByTaskTransaction_TaskId(taskId).stream()
                .map(EntityToDto::transactionEntityToDto)
                .toList();
    }
}
