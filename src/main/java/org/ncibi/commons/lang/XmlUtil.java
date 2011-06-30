package org.ncibi.commons.lang;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.ncibi.commons.exception.ConstructorCalledError;
import org.ncibi.commons.exception.PreConditionCheckFailedException;

/**
 * Utility classes for working with java beans xml.
 * 
 * @author gtarcea
 * 
 */
public class XmlUtil
{
    /**
     * Utility class - only contains static methods.
     */
    private XmlUtil()
    {
        throw new ConstructorCalledError(this.getClass());
    }

    /**
     * Convert an object to an xml string.
     * 
     * @param object
     *            The object to convert.
     * @return An xml string of the object, or null if conversion was unsuccessful.
     * @throws PreConditionCheckFailedException
     *             When object is null.
     */
    public static String toXmlString(final Object object)
    {
        PreCond.require(object != null);

        String xml = null;

        try
        {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            final XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(out));
            encoder.writeObject(object);
            encoder.close();
            out.flush();
            xml = out.toString();
            out.close();
        }
        catch (final IOException e)
        {
            xml = null;
        }

        return xml;
    }
}
