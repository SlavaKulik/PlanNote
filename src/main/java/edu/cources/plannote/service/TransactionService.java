package edu.cources.plannote.service;

import edu.cources.plannote.dto.TransactionDto;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    void addNewTransaction(TransactionDto transaction);

    List<TransactionDto> getTransactionsByTaskId(UUID taskId);

}
