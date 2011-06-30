package org.ncibi.commons.closure;

import static org.junit.Assert.*;

import org.junit.Test;
import org.ncibi.commons.closure.AbstractFieldGetter;
import org.ncibi.commons.closure.ChangerFieldGetter;
import org.ncibi.commons.closure.NamedFieldGetter ;
import org.ncibi.commons.closure.TransformerFieldGetter ;
import org.ncibi.commons.closure.FieldGetter;
import org.ncibi.commons.test.util.TestBean ;

public class FieldGetterTest
{
    @Test
    public void testNamedFieldGetterGetField()
    {
        TestBean testBean = new TestBean(1, "abc") ;
        
        FieldGetter<String, TestBean> fieldGetter = new NamedFieldGetter<String, TestBean>("value") ;
        
        String value = fieldGetter.getField(testBean) ;
        
        assertTrue(value != null) ;
        assertTrue(value.equals("abc")) ;
    }
    
    @Test
    public void testAbstractFieldGetterGetField()
    {
        TestBean testBean = new TestBean(1, "abc") ;
        
        FieldGetter<String, TestBean>fieldGetter = new AbstractFieldGetter<String, TestBean>()
        {
            public String getField(TestBean object)
            {
                return object.getValue() ;
            }
        } ;
        
        String value = fieldGetter.getField(testBean) ;
        assertTrue(value != null) ;
        assertTrue(value.equals("abc")) ;
    }
    
    @Test
    public void testChangerFieldGetter()
    {
        TestBean testBean = new TestBean(1, "abc") ;
        
        FieldGetter<String, TestBean> abstractFieldGetter = new AbstractFieldGetter<String, TestBean>()
        {
            public String getField(TestBean object)
            {
                return object.getValue() ;
            }     
        } ;
        
        ChangerFieldGetter<String, TestBean> changerFieldGetter ;
        
        changerFieldGetter = new ChangerFieldGetter<String, TestBean>(abstractFieldGetter)
        {
            public String changeField(String field)
            {
                return "--" + field + "--" ;
            }
        } ;
        
        String value = changerFieldGetter.getField(testBean) ;
        assertTrue(value.equals("--abc--")) ;
        
        FieldGetter<String, TestBean> namedFieldGetter = new NamedFieldGetter<String, TestBean>("value") ;
        
        changerFieldGetter = new ChangerFieldGetter<String, TestBean>(namedFieldGetter)
        {
            public String changeField(String field)
            {
                return "--" + field + "--" ;
            }
        } ;
        
        value = changerFieldGetter.getField(testBean) ;
        assertTrue(value.equals("--abc--")) ;
        
        changerFieldGetter = new ChangerFieldGetter<String, TestBean>("value")
        {
            public String changeField(String field)
            {
                return "--" + field + "--" ;
            }
        } ;
        
        value = changerFieldGetter.getField(testBean) ;
        assertTrue(value.equals("--abc--")) ;     
    }
    
    @Test
    public void testTransformerFieldGetter()
    {
    	TestBean testBean = new TestBean(1, "abc") ;
    	FieldGetter<String, TestBean> fieldGetter = new TransformerFieldGetter<Integer, String, TestBean>("id")
    	{
    		public String transformField(Integer id)
    		{
    			return Integer.toString(id) ;
    		}
    	} ;
    	
    	String transformedValue = fieldGetter.getField(testBean) ;
    	assertTrue(transformedValue.equals("1")) ;
    }

}
