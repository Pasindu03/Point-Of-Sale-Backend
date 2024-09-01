package com.example.posb.persistance;

import com.example.posb.dto.UserDto;

import java.sql.Connection;
import java.sql.SQLException;

public interface User {
    UserDto getUser(String id, Connection connection) throws SQLException;
    String saveUser(UserDto dto, Connection connection) throws SQLException;
}
