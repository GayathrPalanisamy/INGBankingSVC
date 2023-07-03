package com.ing.banking.service;

import com.ing.banking.data.LoanRequest;
import com.ing.banking.dataModel.Customer;
import com.ing.banking.dataModel.Loan;
import com.ing.banking.exception.CustomerNotFoundException;
import com.ing.banking.exception.LoanCreationException;
import com.ing.banking.repo.CustomerRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LoanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanService.class);
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private Validator validator;

    public double getLoanAmount(int customerId) {
        double loanAmount = 0;

        LOGGER.info("Check if customer is already present " + customerId);
        Optional<Customer> customer = customerRepository.findCustomerByCustomerId(customerId);

        if (customer.isPresent()) {
            LOGGER.info("Customer Id found, retrieving loan details");
            loanAmount = customer.get().getLoans().stream().mapToDouble(i -> i.getLoanAmount()).sum();
        } else {
            LOGGER.warn("Customer Id not found");
            throw new CustomerNotFoundException();
        }
        return loanAmount;
    }

    public Customer createLoanRequest(LoanRequest loanRequest) throws Exception {

        Set<ConstraintViolation<LoanRequest>> violations = validator.validate(loanRequest);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Error occurred: ", violations);
        }

        LOGGER.info("Check if customer is already present " + loanRequest.getCustomerID());
        Optional<Customer> customer = customerRepository.findCustomerByCustomerId(loanRequest.getCustomerID());

        if (customer.isPresent()) {
            LOGGER.info("Customer exist, updating loan details");
            return addLoanDetailsInCustomer(loanRequest, customer.get());
        } else {
            LOGGER.info("Customer does not exist, creating customer and loan details");
            return createCustomerAndLoanDetails(loanRequest);
        }
    }

    private Customer createCustomerAndLoanDetails(LoanRequest loanRequest) {
        try {
            LOGGER.info("Prepare customer details");
            Customer customer = new Customer();
            customer.setCustomerId(loanRequest.getCustomerID());
            customer.setCustomerName(loanRequest.getName());

            List<Loan> loans = prepareLoans(loanRequest);
            customer.setLoans(loans);

            LOGGER.info("Creating customer and loan details");
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new LoanCreationException();
        }
    }

    private Customer addLoanDetailsInCustomer(LoanRequest loanRequest, Customer customer) {
        try {
            List<Loan> loans = prepareLoans(loanRequest);
            customer.getLoans().addAll(loans);

            LOGGER.info("Updating loan details in the already existing customer");
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new LoanCreationException();
        }
    }

    private List<Loan> prepareLoans(LoanRequest loanRequest) {
        LOGGER.info("Prepare loan details");
        List<Loan> loans = new ArrayList<>();
        Loan loan = new Loan();
        loan.setLoanUniqueIdentifier(loanRequest.getLoanUniqueIdentifier());
        loan.setLoanAmount(loanRequest.getLoanAmount());
        loans.add(loan);
        return loans;
    }
}