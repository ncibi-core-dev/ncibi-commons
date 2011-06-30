package org.ncibi.commons.annotations;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.ncibi.commons.test.util.TField;
import org.ncibi.commons.test.util.TMethod;

public class ClassAnnotationCollectorTest
{
    private static class TClass
    {
        @TField(name = "field1")
        private String field1;
        
        @TMethod(name="getField1")
        public String getField1() { return field1; }
    }
    
    @Test
    public final void noTest(){
    	//TODO: define tests for this test class
    	System.out.println("There are no tests definded for this test class");
    }
    
//    @Test
    public final void testOnEachFieldAnnotation()
    {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public final void testOnEachField()
    {
        fail("Not yet implemented"); // TODO
    }

//   @Test
    public final void testOnEachMethodAnnotation()
    {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public final void testOnEachMethod()
    {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public final void testCollect()
    {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public final void testGetFieldResults()
    {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public final void testGetMethodResults()
    {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public final void testSetFinished()
    {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public final void testSetFinishedProcessingFields()
    {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public final void testSetFinishedProcessingMethods()
    {
        fail("Not yet implemented"); // TODO
    }

}
