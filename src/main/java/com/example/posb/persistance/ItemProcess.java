package com.example.posb.persistance;

import com.example.posb.dto.ItemDto;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.sql.Connection;
import java.sql.SQLException;

public final class ItemProcess implements Item {
    static String SAVE_ITEM = "INSERT INTO items (itemCode, name, price, qty) VALUES (?,?,?,?)";
    static String GET_ITEM = "SELECT * FROM items WHERE itemCode = ?";
    static String UPDATE_ITEM = "UPDATE items SET name = ?, price = ?, qty = ? WHERE itemCode = ?";
    static String DELETE_ITEM = "DELETE FROM items WHERE itemCode = ?";

    @Override
    public String saveItem(ItemDto dto, Connection connection) throws SQLException {
        String message = "";

        try {
            var ps = connection.prepareStatement(SAVE_ITEM);
            ps.setString(1, dto.getItemCode());
            ps.setString(2, dto.getItemName());
            ps.setDouble(3, dto.getItemPrice());
            ps.setInt(4, dto.getItemQuantity());

            ps.executeUpdate();

            if (ps.executeUpdate() != 0) {
                message = "Item Saved";
            } else {
                message = "Item Not Saved";
            }

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public String deleteItem(String id, Connection connection) throws SQLException {
        String message = "";

        try{
            var ps = connection.prepareStatement(DELETE_ITEM);
            ps.setString(1, id);

            if (ps.executeUpdate() != 0) {
                message = "Item Deleted";
            } else {
                message = "Item Not Deleted";
            }

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public boolean updateItem(String id, ItemDto dto, Connection connection) throws SQLException {
        try (var ps  = connection.prepareStatement(UPDATE_ITEM)) {
            ps.setString(1, id);
            Jsonb jsonb = JsonbBuilder.create();

            ps.setString(1, dto.getItemName());
            ps.setDouble(2, dto.getItemPrice());
            ps.setInt(3, dto.getItemQuantity());
            ps.setString(4, id);

            if (ps.executeUpdate() != 0) {
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
    public ItemDto getItem(String id, Connection connection) throws SQLException {
        var dto = new ItemDto();

        try {
            var ps = connection.prepareStatement(GET_ITEM);

            ps.setString(1, id);
            var result = ps.executeQuery();

            while (result.next()){
                dto.setItemCode(result.getString("itemCode"));
                dto.setItemName(result.getString("name"));
                dto.setItemPrice(result.getDouble("price"));
                dto.setItemQuantity(result.getInt("qty"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }
}
