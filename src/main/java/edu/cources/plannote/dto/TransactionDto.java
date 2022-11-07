package edu.cources.plannote.dto;

import edu.cources.plannote.entity.TaskEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionDto {
    private String transactionId;

    private String transactionName;

    private String transactionMoneyFlow;

    private TaskEntity task;

    private String sum;
}
