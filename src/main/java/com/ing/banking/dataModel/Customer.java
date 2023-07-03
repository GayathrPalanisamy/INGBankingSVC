package com.ing.banking.dataModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int customerId;
    private String customerName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_refernce_id", referencedColumnName = "id")
    private List<Loan> loans;

}