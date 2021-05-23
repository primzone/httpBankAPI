package com.bank.api.DAO;

import com.bank.api.entity.Contractor;

import java.sql.SQLException;
import java.util.List;

public interface ContractorDAO {
    void addByUserId(long userId, String name, boolean corporation) throws SQLException;

    List<Contractor> findAllByUserId(long userId) throws SQLException;
}
