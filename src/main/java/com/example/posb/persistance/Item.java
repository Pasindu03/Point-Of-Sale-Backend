package com.example.posb.persistance;

import com.example.posb.dto.ItemDto;

import java.sql.Connection;
import java.sql.SQLException;

public sealed interface Item permits ItemProcess{
    String saveItem(ItemDto dto, Connection connection) throws SQLException;
    String deleteItem(String id, Connection connection) throws SQLException;
    boolean updateItem(String id, ItemDto dto, Connection connection) throws SQLException;
    ItemDto getItem(String id, Connection connection) throws SQLException;
}
