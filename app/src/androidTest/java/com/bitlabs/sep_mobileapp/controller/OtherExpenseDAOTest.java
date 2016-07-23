package com.bitlabs.sep_mobileapp.controller;

import android.test.mock.MockContext;

import com.bitlabs.sep_mobileapp.data.OtherExpense;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Date;

/**
 * Created by Chamin on 6/16/2016.
 */
public class OtherExpenseDAOTest extends TestCase {

    OtherExpenseDAO otherExpenseDAO= new OtherExpenseDAO(new MockContext());

    @Test
    public void testSetOtherExpense() throws Exception {
        boolean res=otherExpenseDAO.setOtherExpense(new OtherExpense(1, new Date(), 123132923, "none", 13, "Service", "serrvoi", "One-time cost", "dg-1244", 6.12, 80.12));
        assertTrue(res);
    }
    @Test
    public void testUpdateOtherExpense() throws Exception {
        boolean res=otherExpenseDAO.updateOtherExpense(new OtherExpense(1, new Date(), 123132923, "none", 13, "Service", "serrvoi", "One-time cost", "dg-1244", 6.12, 80.12));
        assertTrue(res);
    }
    @Test
    public void testDeleteOtherExpense() throws Exception {
        boolean res=otherExpenseDAO.deleteOtherExpense(1);
        assertTrue(res);
    }

}