package com.example.posb.persistance;

import com.example.posb.dto.CustomerDto;

import java.sql.Connection;
import java.sql.SQLException;

public final class CustomerProcess implements Customer {
    static String SAVE_CUSTOMER = "INSERT INTO customer (id, name, address, mobile) VALUES (?,?,?,?)";

    @Override
    public String saveCustomer(CustomerDto dto, Connection connection) {
        String msg = "";

        try {
            var preparedStatement = connection.prepareStatement(SAVE_CUSTOMER);
            preparedStatement.setLong(1, dto.getCustomerId());
            preparedStatement.setString(2, dto.getCustomerName());
            preparedStatement.setString(2, dto.getCustomerAddress());
            preparedStatement.setString(2, dto.getCustomerPhone());

            preparedStatement.executeUpdate();

            if (preparedStatement.executeUpdate() != 0){
                msg = "Customer saved successfully";
            } else {
                msg = "Customer save failed";
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
