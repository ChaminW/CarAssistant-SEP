package com.bitlabs.sep_mobileapp.controller;


import android.test.mock.MockContext;

import com.bitlabs.sep_mobileapp.data.FuelFillUp;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Date;


/**
 * Created by Chamin on 6/16/2016.
 */
public class FuelFillUpDAOTest extends TestCase {

    FuelFillUpDAO fuelFillUpDAO = new FuelFillUpDAO(new MockContext());

    @Test
    public void testSetFillUp() throws Exception {


        Boolean result=fuelFillUpDAO.setFillUp(new FuelFillUp(1, new Date(), 1, 20100, 1, 1123, "Diesel", true, 6.7, 80.912, "none"));
        assertTrue(result);
    }

    @Test
    public void testUpdateFillUp() throws Exception {
        Boolean result=fuelFillUpDAO.updateFillUp(new FuelFillUp(1, new Date(), 1, 20100, 1, 1123, "Diesel", true, 6.7, 80.912, "none"));
        assertTrue(result);

    }



    @Test
    public void testDeleteFuelFillUp() throws Exception {
        Boolean result=fuelFillUpDAO.deleteFuelFillUp(1);
        assertTrue(result);

    }



}