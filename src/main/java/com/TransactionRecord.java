package com;

import com.exceptions.AmountException;
import com.exceptions.CurrencyCodeException;
import com.exceptions.LineInputException;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TransactionRecord {
    private String currencyCode;

    private int amount;

    public static TransactionRecord createTransactionRecord(String line) throws LineInputException, CurrencyCodeException, AmountException {
        String[] words = line.split(" ");
        if (words.length != 2) {
            throw new LineInputException();
        }
        if (!words[0].matches("^[a-zA-Z]{3}$")) {
            throw new CurrencyCodeException(words[0]);
        }
        try {
            int amount = Integer.parseInt(words[1]);

            return new TransactionRecord(words[0].toUpperCase(), amount);
        } catch (NumberFormatException ex) {
            throw new AmountException(words[1]);
        }

    }
}
