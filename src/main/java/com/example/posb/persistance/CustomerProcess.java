package com.example.posb.persistance;

import com.example.posb.dto.CustomerDto;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CustomerProcess implements Customer {
    static String SAVE_CUSTOMER = "INSERT INTO customer (id, name, address, mobile) VALUES (?,?,?,?)";
    static String GET_CUSTOMER = "SELECT * FROM customer WHERE id = ?";
    static String UPDATE_CUSTOMER = "UPDATE customer SET name = ?, address = ?, mobile = ? WHERE id = ?";
    static String DELETE_CUSTOMER = "DELETE FROM customer WHERE id = ?";
    static String GET_ALL_CUSTOMER = "SELECT * FROM customer ORDER BY id DESC";


    @Override
    public String saveCustomer(CustomerDto dto, Connection connection) throws SQLException {
        String msg = "";

        try {
            var preparedStatement = connection.prepareStatement(SAVE_CUSTOMER);
            preparedStatement.setString(1, dto.getCustomerId());
            preparedStatement.setString(2, dto.getCustomerName());
            preparedStatement.setString(2, dto.getCustomerAddress());
            preparedStatement.setString(2, dto.getCustomerPhone());

            preparedStatement.executeUpdate();

            if (preparedStatement.executeUpdate() != 0) {
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

    @Override
    public String deleteCustomer(String id, Connection connection) throws SQLException {
        String msg = "";

        try {
            var preparedStatement = connection.prepareStatement(DELETE_CUSTOMER);
            preparedStatement.setString(1, id);

            if (preparedStatement.executeUpdate() != 0) {
                msg = "Customer deleted successfully";
            } else {
                msg = "Customer delete failed";
            }

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return msg;
    }

    @Override
    public boolean updateCustomer(String id, CustomerDto dto, Connection connection) throws SQLException {
        try (var preparedStatement = connection.prepareStatement(UPDATE_CUSTOMER)) {
            preparedStatement.setString(1, id);
            Jsonb jsonb = JsonbBuilder.create();

            preparedStatement.setString(1, dto.getCustomerName());
            preparedStatement.setString(2, dto.getCustomerAddress());
            preparedStatement.setString(3, dto.getCustomerPhone());
            preparedStatement.setString(4, id);

            if (preparedStatement.executeUpdate() != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public CustomerDto getCustomer(String id, Connection connection) throws SQLException {
        var dto = new CustomerDto();
        try {
            var ps = connection.prepareStatement(GET_CUSTOMER);

            ps.setString(1, id);
            var resultSet = ps.executeQuery();

            while (resultSet.next()) {
                dto.setCustomerId(resultSet.getString("id"));
                dto.setCustomerName(resultSet.getString("name"));
                dto.setCustomerAddress(resultSet.getString("address"));
                dto.setCustomerPhone(resultSet.getString("mobile"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dto;
    }

    @Override
    public String getAllCustomer(CustomerDto dto, Connection connection) throws SQLException {
        return "";
    }
}
