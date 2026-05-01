package com.banksystem.model;

import com.banksystem.exception.BankException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tài khoản vãng lai.
 */
public class CheckingAccount extends Account {

    private static final Logger logger = LoggerFactory.getLogger(CheckingAccount.class);

    public CheckingAccount(long accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void deposit(double amount) {
        double initialBalance = getBalance();
        try {
            doDepositing(amount);
            double finalBalance = getBalance();

            Transaction transaction = new Transaction(
                    Transaction.TYPE_DEPOSIT_CHECKING, amount, initialBalance, finalBalance);
            addTransaction(transaction);

            logger.info("Nạp tiền vãng lai thành công. Tài khoản: {}, Số tiền: +{}",
                    getAccountNumber(), amount);
        } catch (BankException e) {
            logger.error("Lỗi nạp tiền vãng lai: {}", e.getMessage(), e);
        }
    }

    @Override
    public void withdraw(double amount) throws BankException {
        double initialBalance = getBalance();

        // Không dùng try-catch ở đây nữa, để lỗi văng ra ngoài
        doWithdrawing(amount);

        double finalBalance = getBalance();
        Transaction transaction = new Transaction(
                Transaction.TYPE_WITHDRAW_CHECKING, amount, initialBalance, finalBalance);
        addTransaction(transaction);

        logger.info("Rút tiền vãng lai thành công. Tài khoản: {}, Số dư mới: {}",
                getAccountNumber(), finalBalance);
    }
}