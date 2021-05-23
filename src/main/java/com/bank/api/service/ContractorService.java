package com.bank.api.service;

import com.bank.api.entity.Contractor;
import com.bank.api.exceptions.MyGlobalException;

import java.sql.SQLException;
import java.util.List;

public interface ContractorService {
    void addByUserId(long userId, String name, boolean corporation) throws SQLException;

    List<Contractor> findAllByUserId(long userId) throws SQLException;
}
