package com.hardware.server.database;

import android.content.Context;

import java.util.List;

public class DatabaseInstance {
    private static DatabaseInstance INSTANCE;
    private List<Order> dborders;
    private OrderDAO orderDAO;

    private DatabaseInstance(OrderDatabase db) {
        this.orderDAO = db.getOrderDAO();
        this.dborders = orderDAO.getOrders();
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public List<Order> getList() {
        return dborders;
    }

    public static DatabaseInstance getDatabaseInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseInstance(OrderDatabase.getItemDatabase(context));
        }
        return INSTANCE;
    }
}
