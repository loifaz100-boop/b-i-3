package com.banksystem.model;

import java.util.Locale;

/**
 * Đại diện cho một giao dịch trong ngân hàng.
 */
public class Transaction {

    public static final int TYPE_DEPOSIT_CHECKING = 1;
    public static final int TYPE_WITHDRAW_CHECKING = 2;
    public static final int TYPE_DEPOSIT_SAVINGS = 3;
    public static final int TYPE_WITHDRAW_SAVINGS = 4;

    private int type;
    private double amount;
    private double initialBalance;
    private double finalBalance;

    /**
     * Khởi tạo một giao dịch mới.
     */
    public Transaction(int type, double amount, double initialBalance, double finalBalance) {
        this.type = type;
        this.amount = amount;
        this.initialBalance = initialBalance;
        this.finalBalance = finalBalance;
    }

    // ... (Các hàm Getter và Setter giữ nguyên, nhớ format cho đúng thụt lề 2 spaces)

    /**
     * Chuyển đổi mã loại giao dịch thành chuỗi ký tự mô tả.
     */
    public static String getTypeString(int transactionType) {
        switch (transactionType) {
            case TYPE_DEPOSIT_CHECKING:
                return "Nạp tiền vãng lai";
            case TYPE_WITHDRAW_CHECKING:
                return "Rút tiền vãng lai";
            case TYPE_DEPOSIT_SAVINGS:
                return "Nạp tiền tiết kiệm";
            case TYPE_WITHDRAW_SAVINGS:
                return "Rút tiền tiết kiệm";
            default:
                return "Không rõ";
        }
    }

    /**
     * Lấy bản tóm tắt giao dịch được format chuẩn.
     */
    public String getTransactionSummary() {
        String formattedInitial = String.format(Locale.US, "%.2f", initialBalance);
        String formattedAmount = String.format(Locale.US, "%.2f", amount);
        String formattedFinal = String.format(Locale.US, "%.2f", finalBalance);

        return String.format("- Kiểu giao dịch: %s. Số dư ban đầu: $%s. Số tiền: $%s. Số dư cuối: $%s.",
                getTypeString(type), formattedInitial, formattedAmount, formattedFinal);
    }
}