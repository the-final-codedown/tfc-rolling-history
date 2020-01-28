package fr.polytech.al.tfc.rollinghistory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTO {

    private Integer money;

    private AccountType accountType;

}
