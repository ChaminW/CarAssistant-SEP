package com.bitlabs.sep_mobileapp.view.viewController;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Date;

/**
 * Created by Chamin on 6/17/2016.
 */
public class UtilsTest extends TestCase {



    @Test
    public void testMonthsBetween() throws Exception {
        Date d1=new Date(2013,7,1);
        Date d2=new Date(2013,10,1);
        int diff=Utils.monthsBetween(d1,d2);
        assertEquals(diff,3);

    }
    @Test
    public void testYearsBetween() throws Exception {

        Date d1=new Date(2011,10,1);
        Date d2=new Date(2013,10,1);
        int diff=Utils.monthsBetween(d1,d2);
        assertEquals(diff,2);

    }
}