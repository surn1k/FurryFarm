package com.furrryfarm.utils.auth;

import com.furrryfarm.db.AccountTable;
import com.furrryfarm.db.DealerTable;
import com.furrryfarm.db.FarmerTable;
import com.furrryfarm.db.entity.Account;
import com.furrryfarm.db.entity.Dealer;
import com.furrryfarm.db.entity.Farmer;

import java.sql.SQLException;

public class CredentialsManager {
    private static final AccountTable accountTable = new AccountTable();
    private static final FarmerTable farmerTable = new FarmerTable();
    private static final DealerTable dealerTable = new DealerTable();

    public static boolean validate(String login, String password) {
        try {
            return accountTable.get(login, password) != null;
        } catch (SQLException | ClassNotFoundException exception) {
            return false;
        }
    }

    public static Integer getID(String login, String password) {
        try {
            return accountTable.get(login, password).id();
        } catch (SQLException | ClassNotFoundException exception) {
            return null;
        }
    }

    public static void registerUser(String login,
                                    String password,
                                    String name,
                                    String type) {
        // Dummy id passed because
        Account account = new Account(0, login, password);
        try {
            accountTable.insert(account);
            Account insertedAccount = accountTable.get(login, password);

            int accountID = insertedAccount.id();
            if (type.equals("farmer")) {
                Farmer newFarmer = new Farmer(accountID, name);
                farmerTable.insert(newFarmer);
            } else {
                Dealer newDealer = new Dealer(accountID, name);
                dealerTable.insert(newDealer);
            }
        } catch (SQLException | ClassNotFoundException exception) {
           System.out.println("[DB Error]: " + exception);
        }
    }
}
