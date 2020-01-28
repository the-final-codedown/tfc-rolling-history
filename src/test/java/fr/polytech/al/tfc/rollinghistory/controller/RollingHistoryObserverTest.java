package fr.polytech.al.tfc.rollinghistory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.polytech.al.tfc.rollinghistory.model.Account;
import fr.polytech.al.tfc.rollinghistory.model.AccountType;
import fr.polytech.al.tfc.rollinghistory.model.Transaction;
import fr.polytech.al.tfc.rollinghistory.repository.AccountRepository;
import fr.polytech.al.tfc.rollinghistory.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RollingHistoryObserverTest {

    @MockBean
    private TransactionControllerQueue transactionControllerQueue;

    private String idAccount1 = "idAccount1";
    private String idAccount2 = "idAccount2";
    @Autowired
    private RollingHistoryObserver rollingHistoryController;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    @Before
    public void setUp() {
        accountRepository.save(new Account(idAccount1, 300, AccountType.CHECK));
        accountRepository.save(new Account(idAccount2, 300, AccountType.CHECK));

        Transaction transaction1 = new Transaction(idAccount1, idAccount2, 29, LocalDateTime.now());
        Transaction transaction2 = new Transaction(idAccount1, idAccount2, 29, LocalDateTime.now().minusDays(7));

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
    }

    @Test
    public void processHistory() throws JsonProcessingException {
        Optional<Account> account1 = accountRepository.findById(idAccount1);
        assertTrue(account1.isPresent());

        assertEquals(0, (int) account1.get().getLastWindow());

        rollingHistoryController.processHistory();

        account1 = accountRepository.findById(idAccount1);
        assertTrue(account1.isPresent());
        assertEquals(29, (int) account1.get().getLastWindow());

        Optional<Account> account2 = accountRepository.findById(idAccount2);
        assertTrue(account2.isPresent());
        assertEquals(0, (int) account2.get().getLastWindow());
    }

}