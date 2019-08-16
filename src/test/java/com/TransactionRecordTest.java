package com;

import com.exceptions.AmountException;
import com.exceptions.CurrencyCodeException;
import com.exceptions.LineInputException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.TransactionRecord.createTransactionRecord;

public class TransactionRecordTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void codeLengthNot3() {
        thrown.expect(CurrencyCodeException.class);
        thrown.expectMessage("hehe is not a valid code. Currency code should be 3 uppercase letters.");
        createTransactionRecord("hehe 123");
    }

    @Test
    public void codeNotLetters() {
        thrown.expect(CurrencyCodeException.class);
        thrown.expectMessage("a12 is not a valid code. Currency code should be 3 uppercase letters.");
        createTransactionRecord("a12 123");
    }

    @Test
    public void amountNotValid() {
        thrown.expect(AmountException.class);
        thrown.expectMessage("asd is not a valid amount.");
        createTransactionRecord("aaa asd");
    }

    @Test
    public void lineNotValid() {
        thrown.expect(LineInputException.class);
        thrown.expectMessage("Invalid line input. One good example of input is 'CNY 100'");
        createTransactionRecord("aaa 123 asdf");
    }

    @Test
    public void successCreateRecord() {
        TransactionRecord transactionRecord = createTransactionRecord("aaa 123");
        Assert.assertEquals("AAA", transactionRecord.getCurrencyCode());
        Assert.assertEquals(123, transactionRecord.getAmount());
    }
}