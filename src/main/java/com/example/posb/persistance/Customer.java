package com.example.posb.persistance;

import com.example.posb.dto.CustomerDto;

import java.sql.Connection;
import java.sql.SQLException;

public sealed interface Customer permits CustomerProcess {
    String saveCustomer(CustomerDto dto, Connection connection) throws SQLException;
    String deleteCustomer(String id, Connection connection) throws SQLException;
    boolean updateCustomer(String id, CustomerDto dto, Connection connection) throws SQLException;
    CustomerDto getCustomer(String id, Connection connection) throws SQLException;
    String getAllCustomer(CustomerDto dto, Connection connection) throws SQLException;
}
