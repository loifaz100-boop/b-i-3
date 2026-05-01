package com.banksystem.core;

import com.banksystem.model.Account;
import com.banksystem.model.CheckingAccount;
import com.banksystem.model.Customer;
import com.banksystem.model.SavingsAccount;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp Bank đại diện cho ngân hàng, quản lý danh sách khách hàng.
 */
public class Bank {

    private static final Logger logger = LoggerFactory.getLogger(Bank.class);

    private List<Customer> customerList;

    public Bank() {
        this.customerList = new ArrayList<>();
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    /**
     * Cập nhật danh sách khách hàng.
     */
    public void setCustomerList(List<Customer> customerList) {
        if (customerList == null) {
            this.customerList = new ArrayList<>();
        } else {
            this.customerList = customerList;
        }
    }

    /**
     * Đọc dữ liệu khách hàng từ InputStream.
     */
    public void readCustomerList(InputStream inputStream) {
        logger.debug("Bắt đầu đọc dữ liệu khách hàng từ luồng đầu vào...");
        if (inputStream == null) {
            logger.warn("InputStream bị null, không thể đọc dữ liệu.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            Customer currentCustomer = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                int lastSpaceIndex = line.lastIndexOf(' ');
                if (lastSpaceIndex > 0) {
                    String token = line.substring(lastSpaceIndex + 1).trim();

                    if (token.matches("\\d{9}")) {
                        String name = line.substring(0, lastSpaceIndex).trim();
                        currentCustomer = new Customer(Long.parseLong(token), name);
                        customerList.add(currentCustomer);
                        logger.info("Đã thêm khách hàng mới: {}", name);
                    } else if (currentCustomer != null) {
                        processAccountLine(line, currentCustomer);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Lỗi khi đọc danh sách khách hàng: {}", e.getMessage(), e);
        }
    }

    /**
     * Xử lý dòng chứa thông tin tài khoản và thêm vào khách hàng hiện tại.
     */
    private void processAccountLine(String line, Customer currentCustomer) {
        String[] parts = line.split("\\s+");
        if (parts.length >= 3) {
            try {
                long accountNumber = Long.parseLong(parts[0]);
                double balance = Double.parseDouble(parts[2]);
                String accountType = parts[1];

                if (Account.CHECKING_TYPE.equals(accountType)) {
                    currentCustomer.addAccount(new CheckingAccount(accountNumber, balance));
                } else if (Account.SAVINGS_TYPE.equals(accountType)) {
                    currentCustomer.addAccount(new SavingsAccount(accountNumber, balance));
                }
            } catch (NumberFormatException e) {
                logger.error("Định dạng số không hợp lệ trong dòng: {}", line, e);
            }
        }
    }

    /**
     * Lấy thông tin khách hàng sắp xếp theo ID.
     */
    public String getCustomersInfoByIdOrder() {
        List<Customer> sortedList = new ArrayList<>(customerList);
        sortedList.sort((c1, c2) -> Long.compare(c1.getIdNumber(), c2.getIdNumber()));

        StringBuilder infoBuilder = new StringBuilder();
        for (int i = 0; i < sortedList.size(); i++) {
            infoBuilder.append(sortedList.get(i).getCustomerInfo());
            if (i < sortedList.size() - 1) {
                infoBuilder.append("\n");
            }
        }
        return infoBuilder.toString();
    }

    /**
     * Lấy thông tin khách hàng sắp xếp theo tên, nếu trùng thì theo ID.
     */
    public String getCustomersInfoByNameOrder() {
        List<Customer> sortedList = new ArrayList<>(customerList);
        sortedList.sort((c1, c2) -> {
            int nameCompare = c1.getFullName().compareTo(c2.getFullName());
            if (nameCompare != 0) {
                return nameCompare;
            }
            return Long.compare(c1.getIdNumber(), c2.getIdNumber());
        });

        StringBuilder infoBuilder = new StringBuilder();
        for (Customer customer : sortedList) {
            infoBuilder.append(customer.getCustomerInfo()).append("\n");
        }
        return infoBuilder.toString().trim();
    }
}