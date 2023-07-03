package com.ing.banking.controller;

import com.ing.banking.data.LoanRequest;
import com.ing.banking.dataModel.Customer;
import com.ing.banking.service.LoanService;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/loan")
public class LoanController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanController.class);

    @Autowired
    LoanService loanService;

    @PostMapping("/loanRequest")
    public ResponseEntity<String> createLoanRequest(@RequestBody LoanRequest loanRequest) throws Exception {
        LOGGER.info("In method create loan request");
        Customer customer = loanService.createLoanRequest(loanRequest);
        return new ResponseEntity<String>("Loan Request created for Customer with ID " + customer.getCustomerId(), HttpStatus.CREATED);
    }

    @GetMapping("/loanDetails/{customerID}")
    public ResponseEntity<String> getLoanAmount(@PathVariable int customerID) {
        LOGGER.info("In method retrieve loan details");
        double totalLoanAmount = loanService.getLoanAmount(customerID);
        return new ResponseEntity<String>("Total loan amount applied by the customer with ID " + customerID + " is " + totalLoanAmount, HttpStatus.OK);
    }
}