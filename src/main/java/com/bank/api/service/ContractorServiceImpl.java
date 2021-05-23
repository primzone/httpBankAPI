package com.bank.api.service;

import com.bank.api.DAO.ContractorDAO;
import com.bank.api.DAO.ContractorDAOImpl;
import com.bank.api.entity.Contractor;
import com.bank.api.exceptions.MyGlobalException;

import java.sql.SQLException;
import java.util.List;

public class ContractorServiceImpl implements ContractorService{
    ContractorDAO contractorDAO = new ContractorDAOImpl();


    @Override
    public void addByUserId(long userId, String name, boolean corporation) throws SQLException {

        contractorDAO.addByUserId(userId, name, corporation);


    }

    @Override
    public List<Contractor> findAllByUserId(long userId) throws SQLException {
        return contractorDAO.findAllByUserId(userId);
    }
}
