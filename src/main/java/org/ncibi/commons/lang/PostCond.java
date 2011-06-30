package org.ncibi.commons.lang;

import org.ncibi.commons.exception.ConstructorCalledError;
import org.ncibi.commons.exception.PostConditionCheckFailedException;

/**
 * Implements some simple post condition checks. This is useful for maintaining
 * invariant conditions still apply. For example it could be used in a method:
 * <nl/>
 * <code>
 *  public List<T> getListFor(String key)
 *  {
 *      List<T> what = map.get(key);
 *      PostCond.require(what != null, "what != null");
 *      return what;
 *  }
 *  </code>
 * 
 * @author gtarcea
 */
public final class PostCond
{
    /**
     * Utility class, no constructor.
     */
    private PostCond()
    {
        throw new ConstructorCalledError(this.getClass());
    }

    /**
     * Checks if expression is true. If it isn't then it throws
     * PostConditionCheckFailedException.
     * 
     * @param check
     *            The boolean expression to check.
     * @throws PostConditionCheckFailedException
     *             when check is false.
     */
    public static void require(final boolean check)
    {
        if (!check)
        {
            throw new PostConditionCheckFailedException(
                    "Post Condition check failed.");
        }
    }

    /**
     * Checks if expression is true. If it isn't then it throws
     * PostConditionCheckFailedException.
     * 
     * @param check
     *            The boolean expression to check.
     * @param what
     *            The message to show in the exception if check is false.
     * @throws PostConditionCheckFailedException
     *             when check is false.
     */
    public static void require(final boolean check, final String what)
    {
        if (!check)
        {
            throw new PostConditionCheckFailedException(
                    "Post Condition check failed: '" + what + "'.");
        }
    }
}
