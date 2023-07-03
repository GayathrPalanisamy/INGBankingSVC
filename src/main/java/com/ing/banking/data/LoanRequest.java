package com.ing.banking.data;

import jakarta.validation.constraints.*;

public class LoanRequest {

	@NotNull(message = "Customer Id must not be empty")
	@Min(value = 1, message = "Please provide valid CustomerId")
	private int customerID;
	@NotBlank(message = "Customer name must not be empty")
	private String name;

	@DecimalMin(value = "500", message = "Loan amount must be greater than or equal to 500")
	@DecimalMax(value = "12000.50", message = "Loan amount must be lesser than or equal to 12000.50")
	private double loanAmount;

	@NotBlank(message = "Loan unique identifier must not be empty")
	private String loanUniqueIdentifier;

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoanUniqueIdentifier() {
		return loanUniqueIdentifier;
	}

	public void setLoanUniqueIdentifier(String loanUniqueIdentifier) {
		this.loanUniqueIdentifier = loanUniqueIdentifier;
	}

	public LoanRequest(int customerID, String name, double loanAmount, String loanUniqueIdentifier) {
		this.customerID = customerID;
		this.name = name;
		this.loanAmount = loanAmount;
		this.loanUniqueIdentifier = loanUniqueIdentifier;
	}
}