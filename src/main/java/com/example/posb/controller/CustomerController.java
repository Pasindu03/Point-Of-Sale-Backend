package com.example.posb.controller;

import com.example.posb.dto.CustomerDto;
import com.example.posb.persistance.CustomerProcess;
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

@WebServlet(urlPatterns = "/customer")
public class CustomerController extends HttpServlet {
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
            //error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        var studentId = req.getParameter("id");
        CustomerProcess process = new CustomerProcess();

        try(var writer = resp.getWriter()) {
            CustomerDto dto = process.getCustomer(studentId, connection);
            resp.setContentType("application/json");
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(dto, writer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDto dto = jsonb.fromJson(req.getReader(), CustomerDto.class);
            CustomerProcess customerProcess = new CustomerProcess();
            writer.write(customerProcess.saveCustomer(dto, connection));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().contains("application/json") || (req.getContentType() == null)){
            //error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        CustomerProcess cp = new CustomerProcess();

        try(var writer = resp.getWriter()) {
            var id = req.getParameter("id");
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDto dto = jsonb.fromJson(req.getReader(), CustomerDto.class);

            if (cp.updateCustomer(id, dto, connection)){
                writer.write("Customer Updated");
            } else {
                writer.write("Customer Not Updated");
            }
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().contains("application/json") || (req.getContentType() == null)){
            //error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        var id = req.getParameter("id");
        CustomerProcess cp = new CustomerProcess();

        try(var writer = resp.getWriter()) {
            String dto = cp.deleteCustomer(id, connection);
            resp.setContentType("application/json");
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(dto, writer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
