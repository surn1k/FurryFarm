package com.furrryfarm.utils.auth;


import com.furrryfarm.db.AccountTable;
import com.furrryfarm.db.DealerTable;
import com.furrryfarm.db.FarmerTable;

import java.sql.SQLException;

public class UserRoleHelper
{
    public enum UserRole {
        FARMER,
        DEALER,
        AUTHORISED,
        OTHER
    }

    public static UserRole getUserRole(int id) {
        try {
            if (!(new DealerTable()).getByID(id).isEmpty())
                return UserRole.DEALER;
            else if (!(new FarmerTable()).getByID(id).isEmpty())
                return UserRole.FARMER;
            else if (!(new AccountTable()).getByID(id).isEmpty())
                return UserRole.AUTHORISED;
        } catch (Exception exception) {
            System.out.println("[DB Error]: " + exception);
        }
        return UserRole.OTHER;
    }
}

