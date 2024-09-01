package com.example.posb.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDetailProcess implements OrderDetail{
    public boolean saveOrderDetails(Connection connection, OrderDetailsDTO orderDetailsDTO){
        String sql = "insert into orderDetails(orderId, itemId, getQty, price) values(?, ?, ?, ?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,orderDetailsDTO.getOrderId());
            preparedStatement.setString(2,orderDetailsDTO.getItemCode());
            preparedStatement.setInt(3,orderDetailsDTO.getGetQty());
            preparedStatement.setDouble(4,orderDetailsDTO.getPrice());

            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
