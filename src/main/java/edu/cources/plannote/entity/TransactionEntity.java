package edu.cources.plannote.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_list")
public class TransactionEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID transactionId;

    @NotBlank(message = "Transaction name is mandatory!")
    @Column(name = "transaction_name")
    private String transactionName;

    @Column(name = "transaction_money_flow")
    private BigDecimal transactionMoneyFlow;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "task_transaction_id")
    private TaskEntity taskTransaction;
}
