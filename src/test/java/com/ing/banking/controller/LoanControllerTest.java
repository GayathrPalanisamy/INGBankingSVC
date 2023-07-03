package com.ing.banking.controller;

import com.ing.banking.AbstractTest;
import com.ing.banking.data.LoanRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;


public class LoanControllerTest extends AbstractTest {

    public static String CREATE_LOAN_URL = "/v1/loan/loanRequest";
    public static String RETRIEVE_LOAN_DETAILS = "/v1/loan/loanDetails/101";

    public static String RETRIEVE_LOAN_DETAILS_UNKNOWN_USER = "/v1/loan/loanDetails/123";

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldCreateCustomerAndLoan() throws Exception {
        String inputJson = mapToJson(getLoanRequest());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_LOAN_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void retrieveCustomerLoanDetails() throws Exception {

        String inputJson = mapToJson(getLoanRequest());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_LOAN_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        mvcResult = mvc.perform(MockMvcRequestBuilders.get(RETRIEVE_LOAN_DETAILS)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void throwErrorWhenCustomerNotFound() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(RETRIEVE_LOAN_DETAILS)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Customer Id not found"));
    }

    @Test
    public void throwErrorWhenCustomerIdNotSent() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(RETRIEVE_LOAN_DETAILS_UNKNOWN_USER)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    public void throwErrorMessageWhenNameEmpty() throws Exception {
        LoanRequest loanRequest = getLoanRequest();
        loanRequest.setName("");
        String inputJson = mapToJson(loanRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_LOAN_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Customer name must not be empty"));
    }

    @Test
    public void throwErrorMessageWhenInvalidCustomerId() throws Exception {
        LoanRequest loanRequest = getLoanRequest();
        loanRequest.setCustomerID(0);
        String inputJson = mapToJson(loanRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_LOAN_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Please provide valid CustomerId"));
    }

    @Test
    public void throwErrorMessageWhenLessLoanAmount() throws Exception {
        LoanRequest loanRequest = getLoanRequest();
        loanRequest.setLoanAmount(100);
        String inputJson = mapToJson(loanRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_LOAN_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Loan amount must be greater than or equal to 500"));
    }

    @Test
    public void throwErrorMessageWhenHighLoanAmount() throws Exception {
        LoanRequest loanRequest = getLoanRequest();
        loanRequest.setLoanAmount(13000);
        String inputJson = mapToJson(loanRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_LOAN_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Loan amount must be lesser than or equal to 12000.50"));
    }

    private LoanRequest getLoanRequest() {
        LoanRequest loanRequest = new LoanRequest(101, "XYZ", 565, "loan_1");
        return loanRequest;
    }
}
