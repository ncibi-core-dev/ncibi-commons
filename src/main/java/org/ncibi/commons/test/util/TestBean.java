package org.ncibi.commons.test.util;

public class TestBean
{
    private int id;
    private String value;

    public TestBean()
    {
        id = 0;
        value = "";
    }

    public TestBean(int id, String value)
    {
        this.id = id;
        this.value = value;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "TestBean [id=" + this.id + ", value=" + this.value + "]";
    }
}
