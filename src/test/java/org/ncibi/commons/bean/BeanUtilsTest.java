package org.ncibi.commons.bean;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.ncibi.commons.closure.NamedFieldGetter;
import org.ncibi.commons.test.util.TestBean;

public class BeanUtilsTest
{

    @Test
    public void testFieldToList()
    {
        ArrayList<TestBean> beanList = new ArrayList<TestBean>();

        for (int i = 0; i < 3; i++)
        {
            beanList.add(new TestBean(i, Integer.toString(i)));
        }

        List<Integer> intFields = BeanUtils.fieldToList(beanList,
                    new NamedFieldGetter<Integer, TestBean>("id"));

        assertTrue(intFields.size() == 3);
        for (int i = 0; i < 3; i++)
        {
            assertTrue(intFields.get(i) == i);
        }
    }

    @Test
    public void testBeanToMapOfFields()
    {
        TestBean tbean = new TestBean();
        tbean.setValue("hello");
        Map<String, String> beanFieldMap = BeanUtils.beanToMapOfFields(tbean);
        Assert.assertEquals(2, beanFieldMap.size());
        Assert.assertEquals("0", beanFieldMap.get("id"));
        Assert.assertEquals("hello", beanFieldMap.get("value"));
    }

}
