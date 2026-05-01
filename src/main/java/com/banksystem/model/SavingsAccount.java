package com.banksystem.model;

import com.banksystem.exception.InsufficientFundsException;
import com.banksystem.exception.InvalidFundingAmountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tài khoản tiết kiệm - Lớp này thực thi các quy định về rút tiền và nạp tiền.
 */
public class SavingsAccount extends Account {

    private static final Logger logger = LoggerFactory.getLogger(SavingsAccount.class);

    // Sửa lỗi Magic Number: Định nghĩa hằng số rõ ràng
    private static final double MAX_WITHDRAW = 1000.0;
    private static final double MIN_BALANCE = 5000.0;

    public SavingsAccount(long accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void deposit(double amount) {
        logger.debug("Bắt đầu xử lý giao dịch nạp tiền tiết kiệm...");
        double initialBalance = getBalance();
        try {
            doDepositing(amount);
            double finalBalance = getBalance();

            Transaction transaction = new Transaction(
                    Transaction.TYPE_DEPOSIT_SAVINGS, amount, initialBalance, finalBalance);
            addTransaction(transaction);

            logger.info("Nạp tiền thành công. Tài khoản: {}, Số tiền: +{}", getAccountNumber(), amount);
        } catch (InvalidFundingAmountException e) {
            logger.error("Lỗi nạp tiền do số tiền không hợp lệ: {}", e.getMessage(), e);
        }
    }

    @Override
    public void withdraw(double amount) {
        double initialBalance = getBalance();
        try {
            if (amount > MAX_WITHDRAW) {
                throw new InvalidFundingAmountException(amount);
            }
            if (initialBalance - amount < MIN_BALANCE) {
                throw new InsufficientFundsException(amount);
            }

            doWithdrawing(amount);
            double finalBalance = getBalance();

            Transaction transaction = new Transaction(
                    Transaction.TYPE_WITHDRAW_SAVINGS, amount, initialBalance, finalBalance);
            addTransaction(transaction);

            logger.info("Rút tiền tiết kiệm thành công. Số tiền: {}, Số dư mới: {}", amount, finalBalance);
        } catch (InvalidFundingAmountException | InsufficientFundsException e) {
            logger.error("Giao dịch rút tiền thất bại: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Lỗi hệ thống không xác định khi rút tiền", e);
        }
    }
}