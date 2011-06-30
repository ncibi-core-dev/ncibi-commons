package org.ncibi.commons.lang;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.ncibi.commons.lang.StrUtils;
import org.ncibi.commons.test.util.TestBean;
import org.ncibi.commons.closure.NamedFieldGetter;
import org.ncibi.commons.closure.AbstractFieldGetter;

public class StrUtilsTest
{
    @Test
    public void testQuoteFieldJoinCollection()
    {
        ArrayList<TestBean> items = new ArrayList<TestBean>();

        for (int i = 0; i < 3; i++)
        {
            items.add(new TestBean(i, Integer.toString(i) + "0000.5"));
        }

        String quotedFields = StrUtils.quoteFieldJoin(items,
                StrUtils.Quote.SINGLE_QUOTE, ",",
                new AbstractFieldGetter<String, TestBean>()
                {
                    public String getField(TestBean item)
                    {
                        return item.getValue();
                    }
                });

        assertTrue(quotedFields.equals("'00000.5','10000.5','20000.5'"));
        
        String quotedFields2 = StrUtils.quoteFieldJoin(items,
                StrUtils.Quote.DOUBLE_QUOTE, ",",
                new NamedFieldGetter<String, TestBean>("value"));
        
        assertTrue(quotedFields2.equals("\"00000.5\",\"10000.5\",\"20000.5\""));
        
    }

    @Test
    public void testQuoteFieldJoinArray()
    {
        ArrayList<TestBean> items = new ArrayList<TestBean>();

        for (int i = 0; i < 3; i++)
        {
            items.add(new TestBean(i, Integer.toString(i) + "0000.5"));
        }

        TestBean[] itemsAsArray = items.toArray(new TestBean[items.size()]);

        String quotedFields = StrUtils.quoteFieldJoin(itemsAsArray,
                StrUtils.Quote.DOUBLE_QUOTE, ",",
                new AbstractFieldGetter<String, TestBean>()
                {
                    public String getField(TestBean item)
                    {
                        return item.getValue();
                    }
                });

        assertTrue(quotedFields.equals("\"00000.5\",\"10000.5\",\"20000.5\""));

        String quotedFields2 = StrUtils.quoteFieldJoin(itemsAsArray,
                StrUtils.Quote.SINGLE_QUOTE, ",",
                new NamedFieldGetter<String, TestBean>("value"));
        
        assertTrue(quotedFields2.equals("'00000.5','10000.5','20000.5'"));        
    }
    
    @Test
    public void testSplitUsingCharacter()
    {
        String line = "a\t\tb\tc" ;
        List<String> tokens = StrUtils.split(line, '\t') ;
        
        assertTrue(tokens.size() == 4) ;
        assertTrue("a".equals(tokens.get(0))) ;
        assertTrue("".equals(tokens.get(1))) ;
    }
    
    @Test
    public void testSplitUsingString()
    {
        String line = "a:::b:::c" ;
        List<String> tokens = StrUtils.split(line, ":::") ;
        assertTrue(tokens.size() == 3) ;
        assertTrue("a".equals(tokens.get(0))) ;
        
        line = "a::::::b:::c" ;
        tokens = StrUtils.split(line, ":::") ;
        assertTrue(tokens.size() == 4) ;
        assertTrue("a".equals(tokens.get(0))) ;
        assertTrue("".equals(tokens.get(1))) ;
    }
    
    @Test
    public void testSplitAsArrayUsingCharacter()
    {
        String line = "a\t\tb\tc" ;
        String[] tokens = StrUtils.splitAsArray(line, '\t') ;
        
        assertTrue(tokens.length == 4) ;
        assertTrue("a".equals(tokens[0])) ;
        assertTrue("".equals(tokens[1])) ;
    }
    
    @Test
    public void testSplitAsArrayUsingString()
    {
        String line = "a:::b:::c" ;
        String[] tokens = StrUtils.splitAsArray(line, ":::") ;
        assertTrue(tokens.length == 3) ;
        assertTrue("a".equals(tokens[0])) ;
        
        line = "a::::::b:::c" ;
        tokens = StrUtils.splitAsArray(line, ":::") ;
        assertTrue(tokens.length == 4) ;
        assertTrue("a".equals(tokens[0])) ;
        assertTrue("".equals(tokens[1])) ;
    }
}
