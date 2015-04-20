package com.ft.paasport.codedeploy.acceptancetests.tests;

import com.ft.paasport.codedeploy.acceptancetests.testssupport.AbstractAcceptanceTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author anuragkapur
 */
public class DummyAcceptanceTest extends AbstractAcceptanceTest {

    @Test
    public void testCanGetExampleDotCom() throws Exception {
        assertEquals("Hello World!", getConfig().getGreeting());
        assertTrue(true);
    }
}
