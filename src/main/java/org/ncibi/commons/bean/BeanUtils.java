package org.ncibi.commons.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ncibi.commons.closure.FieldGetter;
import org.ncibi.commons.exception.ConstructorCalledError;

/**
 * Class of static utilities functions that can be used on Bean and Bean like
 * objects.
 * 
 * @author V. Glenn Tarcea
 * 
 */
public final class BeanUtils
{
    /**
     * Hide the constructor for the class of statics.
     */
    private BeanUtils()
    {
        throw new ConstructorCalledError(this.getClass());
    }

    /**
     * Converts a field in a list of objects into a list of objects of that
     * field.
     * 
     * @param <ReturnType>
     *            The type of object for the field.
     * @param <BeanType>
     *            The bean type to extract fields from.
     * @param beanList
     *            The list of beans.
     * @param fieldGetter
     *            The field getter that extracts the field from each bean.
     * @return A list of the field objects extracted from each bean.
     */
    public static <ReturnType, BeanType> List<ReturnType> fieldToList(
            final Collection<BeanType> beanList, final FieldGetter<ReturnType, BeanType> fieldGetter)
    {
        final List<ReturnType> fieldList = new ArrayList<ReturnType>();

        for (BeanType bean : beanList)
        {
            final ReturnType field = fieldGetter.getField(bean);
            fieldList.add(field);
        }

        return fieldList;
    }
    
    public static <T> Map<String, String> beanToMapOfFields(T bean)
    {
        Map<String, String> fieldMap = new HashMap<String, String>();
        Class<?> cls = bean.getClass();
        final Field[] fields = cls.getDeclaredFields();
        for (Field field : fields)
        {
            field.setAccessible(true);
            Object value = getFieldValueForObject(field, bean);
            String fieldName = field.getName();
            fieldMap.put(fieldName, value == null ? null : value.toString());
        }
        
        return fieldMap;
    }
    
    private static Object getFieldValueForObject(Field field, Object obj)
    {
        try
        {
            return field.get(obj);
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
        catch (IllegalAccessException e)
        {
            return null;
        }
    }
}
