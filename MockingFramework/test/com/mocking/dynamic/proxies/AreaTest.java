package com.mocking.dynamic.proxies;

import classes.SubClassMocker;
import org.junit.Before;
import org.junit.Test;
import org.mocking.framework.example.classes.Area;

import static org.junit.Assert.assertEquals;


public class AreaTest {
    Area areaMock;

    @Before
    public void setUp() throws Exception {
        areaMock = (Area) SubClassMocker.createMock(Area.class);
    }


    @Test
    public void testDefaultArea(){
        assertEquals(10, areaMock.getDefaultArea(), 0);


    }
}
