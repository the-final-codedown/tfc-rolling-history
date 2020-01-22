package fr.polytech.al.tfc.rollinghistory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.polytech.al.tfc.account.model.Account;
import fr.polytech.al.tfc.account.model.Cap;
import fr.polytech.al.tfc.account.model.Transaction;
import fr.polytech.al.tfc.account.repository.AccountRepository;
import fr.polytech.al.tfc.account.repository.TransactionRepository;
import fr.polytech.al.tfc.rollinghistory.model.Cap;
import fr.polytech.al.tfc.rollinghistory.model.Transaction;
import fr.polytech.al.tfc.rollinghistory.producer.RollingHistoryProducer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@EnableScheduling
@Component
public class RollingHistoryObserver {

    private final RollingHistoryProducer rollingHistoryProducer;

    public RollingHistoryObserver(RollingHistoryProducer rollingHistoryProducer) {
        this.rollingHistoryProducer = rollingHistoryProducer;
    }

    @Scheduled(fixedDelay = 5000)
    public void processHistory() throws JsonProcessingException {
        System.out.println("Processing history");
        List<Transaction> transactions =
        for (Transaction transaction : transactions) {
            List<Transaction> window = transactionRepository.findAllBySourceAndDateAfter(account.getAccountId(), LocalDateTime.now().minusDays(7));
            Integer windowAmount = window.stream()
                    .map(Transaction::getAmount)
                    .reduce(0, Integer::sum);
            if (!windowAmount.equals(account.getLastWindow())) {
                System.out.println("Updating sliding window : " + windowAmount);
                rollingHistoryProducer.sendCap(account.getAccountId(), new Cap(account.getMoney(), account.getAmountSlidingWindow() - account.getLastWindow()));
            }
        }
    }
}
