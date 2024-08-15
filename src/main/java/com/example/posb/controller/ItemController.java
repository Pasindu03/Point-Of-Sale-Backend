package com.example.posb.controller;

import com.example.posb.dto.ItemDto;
import com.example.posb.persistance.ItemProcess;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/items")
public class ItemController extends HttpServlet {
    Connection connection;

    @Override
    public void init() throws ServletException {
        try{
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/pointSale");
            this.connection = pool.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().contains("application/json") || (req.getContentType() == null)){
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        var itemCode = req.getParameter("itemCode");
        ItemProcess process = new ItemProcess();

        try(var writer = resp.getWriter()){
            ItemDto dto = process.getItem(itemCode, connection);
            resp.setContentType("application/json");
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(dto, writer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().contains("application/json") || (req.getContentType() == null)){
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        try(var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            ItemDto dto = jsonb.fromJson(req.getReader(), ItemDto.class);
            ItemProcess process = new ItemProcess();
            writer.write(process.saveItem(dto, connection));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().contains("application/json") || (req.getContentType() == null)){
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        ItemProcess process = new ItemProcess();

        try (var writer = resp.getWriter()){
            var id = req.getParameter("itemCode");
            Jsonb jsonb = JsonbBuilder.create();
            ItemDto dto = jsonb.fromJson(req.getReader(), ItemDto.class);

            if (process.updateItem(id, dto, connection)) {
                writer.write("Item Updated");
            } else {
                writer.write("Item Not Updated");
            }
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().contains("application/json") || (req.getContentType() == null)){
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        var id = req.getParameter("itemCode");
        ItemProcess process = new ItemProcess();

        try(var writer = resp.getWriter()){
            String dto = process.deleteItem(id, connection);
            resp.setContentType("application/json");
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(dto, writer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
