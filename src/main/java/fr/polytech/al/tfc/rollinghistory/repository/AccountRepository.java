package fr.polytech.al.tfc.rollinghistory.repository;

import fr.polytech.al.tfc.rollinghistory.model.Account;
import fr.polytech.al.tfc.rollinghistory.model.AccountType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tfc.account")
public interface AccountRepository extends MongoRepository<Account, String> {

    List<Account> findAllByAccountType(AccountType accountType);
}
