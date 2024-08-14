package com.example.posb.persistance;

import com.example.posb.dto.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;

public sealed interface Customer permits CustomerProcess {
    String saveCustomer(CustomerDto dto, Connection connection);
}
