package org.ncibi.commons.test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class MiscTest
{
	@Test
	//@SuppressWarnings("unchecked")
	public void testDisjunction()
	{
		ArrayList<Integer> list1 = new ArrayList<Integer>() ;
		ArrayList<Integer> list2 = new ArrayList<Integer>() ;
		
		for (int i = 0 ; i < 5 ; i++)
		{
			list1.add(i) ;
		}
		
		for (int j = 0 ; j < 2 ; j++)
		{
			list2.add(j) ;
		}
		

//		Collection<Integer> dis = CollectionUtils.disjunction(list1, list2) ;
		
//		for (Integer i : dis)
//		{
//			//System.out.println("i = " + i) ;
//		}
		
		//System.out.println("\n\n") ;
//		dis = CollectionUtils.disjunction(list2, list1) ;
		
//		for (Integer i : dis)
//		{
//			//System.out.println("i = " + i) ;
//		}
		
		assertTrue(true) ;
	}
	
	@Test
	public void readTest()
	{
	    try
        {
            BufferedReader breader = new BufferedReader(new FileReader("/etc/passwd"));
            String line ;
            while ((line = breader.readLine()) != null)
            {
                System.out.println(line) ;
            }
            breader.close();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            
        }
	}
	
	@Test
	public void readTest2()
	{
//	    InputLineStreamProcessor lineProcessor = new InputLineStreamProcessor()
//	    {
//            @Override
//            public void processLine(String line) throws IOException
//            {
//                System.out.println(line);
//            }
//	    };
	    
	    //lineProcessor.process("/etc/passwd");
	}
	
}
