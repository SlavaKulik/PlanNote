package edu.cources.plannote.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "transaction_list")
public class TransactionEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID transactionId;

    @Column(name = "transaction_name")
    private String transactionName;

    @Column(name = "transaction_money_flow")
    private BigDecimal transactionMoneyFlow;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "task_transaction_id")
    private ProjectEntity taskTransaction;
}
