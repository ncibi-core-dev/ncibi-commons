package org.ncibi.commons.bean;

/**
 * A Bean for storing pairs of items.
 * 
 * @author gtarcea
 *
 * @param <T1>
 * @param <T2>
 */
public class Pair<T1,T2>
{
    private T1 first;
    private T2 second;
    
    public Pair()
    {
    }
    
    public Pair(T1 first, T2 second)
    {
        this.first = first;
        this.second = second;
    }

    /**
     * @return the first
     */
    public T1 getFirst()
    {
        return first;
    }

    /**
     * @param first the first to set
     */
    public void setFirst(T1 first)
    {
        this.first = first;
    }

    /**
     * @return the second
     */
    public T2 getSecond()
    {
        return second;
    }

    /**
     * @param second the second to set
     */
    public void setSecond(T2 second)
    {
        this.second = second;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Pair))
        {
            return false;
        }
        Pair other = (Pair) obj;
        if (first == null)
        {
            if (other.first != null)
            {
                return false;
            }
        }
        else if (!first.equals(other.first))
        {
            return false;
        }
        if (second == null)
        {
            if (other.second != null)
            {
                return false;
            }
        }
        else if (!second.equals(other.second))
        {
            return false;
        }
        return true;
    }
}
