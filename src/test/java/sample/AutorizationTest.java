package sample;

import static org.junit.Assert.*;

public class AutorizationTest {

    @org.junit.Test
    public void app5() {
        String name="dlh4o1tzrbse@yahoo.com";
        String password="2L6KZG";
        Autorization autorization=new Autorization();
        assertEquals(true,autorization.app5(name,password));
    }
}