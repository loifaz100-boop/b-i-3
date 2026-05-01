package com.banksystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp đại diện cho một khách hàng trong hệ thống.
 */
public class Customer {

    private long idNumber;
    private String fullName;
    private List<Account> accountList;

    /**
     * Constructor không tham số (phục vụ mục đích test).
     */
    public Customer() {
        this(0L, "");
    }

    /**
     * Constructor khởi tạo khách hàng với ID và họ tên.
     */
    public Customer(long idNumber, String fullName) {
        this.idNumber = idNumber;
        this.fullName = fullName;
        this.accountList = new ArrayList<>();
    }

    public long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(long idNumber) {
        this.idNumber = idNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    /**
     * Cập nhật danh sách tài khoản của khách hàng.
     */
    public void setAccountList(List<Account> accountList) {
        if (accountList == null) {
            this.accountList = new ArrayList<>();
        } else {
            this.accountList = accountList;
        }
    }

    /**
     * Thêm tài khoản cho khách hàng.
     */
    public void addAccount(Account account) {
        if (account == null) {
            return;
        }
        if (!accountList.contains(account)) {
            accountList.add(account);
        }
    }

    /**
     * Xóa tài khoản khỏi khách hàng.
     */
    public void removeAccount(Account account) {
        if (account == null) {
            return;
        }
        accountList.remove(account);
    }

    /**
     * Lấy thông tin khách hàng dạng chuỗi.
     */
    public String getCustomerInfo() {
        return "Số CMND: " + idNumber + ". Họ tên: " + fullName + ".";
    }
}