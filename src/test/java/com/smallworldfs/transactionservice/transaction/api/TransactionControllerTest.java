package com.smallworldfs.transactionservice.transaction.api;

import static com.smallworldfs.starter.servicetest.error.ErrorDtoResultMatcher.errorDto;
import static com.smallworldfs.transactionservice.transaction.error.TransactionIssue.TRANSACTION_NOT_FOUND;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.smallworldfs.transactionservice.transaction.service.TransactionService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService service;

    @Nested
    class GetTransaction {

        @Test
        void returns_404_when_transaction_does_not_exist() throws Exception {
            int transactionId = 55;
            whenTransactionIsQueriedThenThrowNotFoundException(transactionId);

            ResultActions result = getTransaction(transactionId);

            result.andExpect(status().isNotFound())
                    .andExpect(errorDto()
                            .hasMessage("Transaction with id '55' could not be found.")
                            .hasType("NOT_FOUND")
                            .hasCode("TRANSACTION_NOT_FOUND"));
        }

        private void whenTransactionIsQueriedThenThrowNotFoundException(int id) {
            when(service.getTransaction(id))
                    .thenThrow(TRANSACTION_NOT_FOUND.withParameters(id).asException());
        }

        private ResultActions getTransaction(int id) throws Exception {
            return mockMvc.perform(get("/transactions/{id}", id));
        }
    }
}
