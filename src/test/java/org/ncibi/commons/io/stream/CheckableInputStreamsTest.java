package org.ncibi.commons.io.stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//import org.apache.commons.io.input.AutoCloseInputStream;
import org.junit.Test;

public class CheckableInputStreamsTest
{

    @Test
    public final void testUsingFileInputStream() throws IOException, FileNotFoundException
    {
        System.out.println("\n\ntestUsingFileInputStream()");
        CheckableInputStream is = new CheckableInputStream(new FileInputStream(
                ClassLoader.getSystemResource("emptydata.txt").getFile()));
        assertTrue(is.isOpen());
        is.read();
        assertTrue(is.isOpen());
        is.close();
        assertFalse(is.isOpen());
    }

//    @Test
//    public final void testUsingAutoClose() throws IOException, FileNotFoundException
//    {
//        System.out.println("\n\n");
//        CheckableInputStream is = CheckableInputStreams.openCheckableAutoCloseInputStream(new FileInputStream(
//                ClassLoader.getSystemResource("emptydata.txt").getFile()));
//        assertTrue(is.isOpen());
//        is.read();
//        assertTrue(is.isOpen());
//        is.read();
//        assertFalse(is.isOpen());
//        is.close();
//        assertFalse(is.isOpen());
//    }
    
    @Test
    public final void testUsingAutoClose() throws IOException, FileNotFoundException
    {
        System.out.println("\n\ntestUsingAutoClose()");
        CheckableInputStream is = CheckableInputStream.newAutoCloseInputStream(new FileInputStream(
                ClassLoader.getSystemResource("emptydata.txt").getFile()));
        //assertTrue(is.isOpen());
        System.out.println("after asAutoCloseInputStream = " + is.isOpen());
        is.read();
        System.out.println("after read = " + is.isOpen());
        is.read();
        System.out.println("after read = " + is.isOpen());
        //assertTrue(is.isOpen());
        is.close();
        System.out.println("after close = " + is.isOpen());
        //assertFalse(is.isOpen());
    }  
}
