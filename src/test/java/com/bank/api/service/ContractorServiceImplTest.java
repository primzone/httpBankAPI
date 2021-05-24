package com.bank.api.service;

import com.bank.api.DAO.CardDAOImpl;
import com.bank.api.DAO.ContractorDAO;
import com.bank.api.DAO.ContractorDAOImpl;
import com.bank.api.entity.Card;
import com.bank.api.entity.Contractor;
import com.bank.api.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContractorServiceImplTest {

    @InjectMocks
    ContractorServiceImpl contractorService;

    @Mock
    ContractorDAOImpl contractorDAOMOCK = ContractorDAOImpl.getInstance();

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        try {
            Utils.createAndInitDb();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void addByUserId() {
        try {
            contractorService.addByUserId(1L, "Kolya", false);
            Mockito.verify(contractorDAOMOCK, Mockito.times(1))
                    .addByUserId(1L, "Kolya", false);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    void findAllByUserId() {

        try {
            List<Contractor> contractorsFromMock = new ArrayList<>();
            Mockito.when(contractorDAOMOCK.findAllByUserId(1L)).thenReturn(contractorsFromMock);
            List<Contractor> contractors = contractorService.findAllByUserId(1L);
            assertEquals(contractors, contractorsFromMock);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }
}