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
    void testDepositChecking() { // Thêm throws Exception nếu cần, nhưng deposit thường không ném lỗi
        checkingAccount.deposit(500.0);
        assertEquals(1500.0, checkingAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawCheckingSuccess() throws Exception { // THÊM DÒNG NÀY VÀO ĐÂY!
        checkingAccount.withdraw(200.0);
        assertEquals(800.0, checkingAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawSavingsLimit() {
        // Với assertThrows thì không cần thêm throws ở tên hàm test,
        // vì bản thân assertThrows đã xử lý cái lambda đó rồi.
        assertThrows(InvalidFundingAmountException.class, () -> {
            savingsAccount.withdraw(1500.0);
        }, "Phải ném ra lỗi khi rút quá hạn mức cho phép");
    }
}