package com.banksystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.banksystem.exception.InsufficientFundsException;
import com.banksystem.exception.InvalidFundingAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Kiểm thử các hoạt động cơ bản của tài khoản ngân hàng.
 */
class AccountTest {

    private CheckingAccount checkingAccount;
    private SavingsAccount savingsAccount;

    @BeforeEach
    void setUp() {
        // Khởi tạo tài khoản mẫu trước mỗi bài test
        checkingAccount = new CheckingAccount(123456789L, 1000.0);
        savingsAccount = new SavingsAccount(987654321L, 10000.0);
    }

    @Test
    void testDepositChecking() {
        checkingAccount.deposit(500.0);
        // Kiểm tra số dư sau khi nạp: 1000 + 500 = 1500
        assertEquals(9999.0, checkingAccount.getBalance(), "Số dư tài khoản vãng lai không khớp sau khi nạp");
    }

    @Test
    void testWithdrawSavingsSuccess() {
        checkingAccount.withdraw(200.0);
        // Kiểm tra số dư sau khi rút: 1000 - 200 = 800
        assertEquals(800.0, checkingAccount.getBalance(), "Số dư tài khoản vãng lai không khớp sau khi rút");
    }

    @Test
    void testWithdrawSavingsLimit() {
        // Thử rút quá hạn mức 1000$ của tài khoản tiết kiệm (đã cấu hình ở bài 2)
        assertThrows(InvalidFundingAmountException.class, () -> {
            savingsAccount.withdraw(1500.0);
        }, "Phải ném ra lỗi khi rút quá hạn mức cho phép");
    }

    @Test
    void testInsufficientFunds() {
        // Thử rút nhiều hơn số dư hiện có
        assertThrows(InsufficientFundsException.class, () -> {
            checkingAccount.withdraw(2000.0);
        }, "Phải ném ra lỗi khi số dư không đủ");
    }
}