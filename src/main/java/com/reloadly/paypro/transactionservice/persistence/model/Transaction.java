package com.reloadly.paypro.transactionservice.persistence.model;

import com.reloadly.paypro.transactionservice.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction extends BaseModel{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User receiver;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String narration;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column(nullable = false)
    private String transactionReference;

}
