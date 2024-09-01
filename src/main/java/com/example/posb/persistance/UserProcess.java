package com.example.posb.persistance;

import com.example.posb.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProcess implements User {
    @Override
    public UserDto getUser(String id, Connection connection) throws SQLException {
        String sql = "select * from user where userName=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return new UserDto(
                        resultSet.getString("userName"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String saveUser(UserDto dto, Connection connection) throws SQLException {
        String sql = "insert into user(userName,email,password) values(?,?,?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dto.getUserName());
            preparedStatement.setString(2, dto.getEmail());
            preparedStatement.setString(3, dto.getPassword());

            return String.valueOf(preparedStatement.executeUpdate() != 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
