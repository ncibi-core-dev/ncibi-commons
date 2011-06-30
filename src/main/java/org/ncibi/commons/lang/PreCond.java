package org.ncibi.commons.lang;

import org.ncibi.commons.exception.ConstructorCalledError;
import org.ncibi.commons.exception.PreConditionCheckFailedException;

/**
 * Implements some simple pre condition checks. This is useful for ensuring that
 * certain conditions still apply or arguments are valid. For example it could
 * be used in a constructor:
 * <nl/>
 * <code>
 *  public MyConstructor(int a)
 *  {
 *      PreCond.require(a > 0, "a > 0"); 
 *      this.a = a; 
 *  }
 *  </code>
 * 
 * @author gtarcea
 */
public final class PreCond
{
    /**
     * Utility class, no constructor.
     */
    private PreCond()
    {
        throw new ConstructorCalledError(this.getClass());
    }

    /**
     * Checks if expression is true. If it isn't then it throws
     * PreConditionCheckFailedException.
     * 
     * @param check
     *            The boolean expression to check.
     * @throws PreConditionCheckFailedException
     *             when check is false.
     */
    public static void require(final boolean check)
    {
        if (!check)
        {
            throw new PreConditionCheckFailedException(
                    "Pre Condition check failed.");
        }
    }

    /**
     * Checks if expression is true. If it isn't then it throws
     * PreConditionCheckFailedException.
     * 
     * @param check
     *            The boolean expression to check.
     * @param what
     *            The message to show in the exception if check is false.
     * @throws PreConditionCheckFailedException
     *             when check is false.
     */
    public static void require(final boolean check, final String what)
    {
        if (!check)
        {
            throw new PreConditionCheckFailedException(
                    "Pre Condition check failed: '" + what + "'.");
        }
    }

    /**
     * Checks if expression is true. This method is the same as require(),
     * except that it throws an IllegalArgumentException. This method is meant
     * for checking preconditions on arguments.
     * 
     * @param check
     *            The boolean expression to check.
     * @throws IllegalArgumentException
     *             when check is false.
     */
    public static void requireArg(final boolean check)
    {
        if (!check)
        {
            throw new IllegalArgumentException("Pre Condition check failed.");
        }
    }

    /**
     * Checks if expression is true. This method is the same as require(),
     * except that it throws an IllegalArgumentException. This method is meant
     * for checking preconditions on arguments.
     * 
     * @param check
     *            The boolean expression to check.
     * @param what
     *            The message to show in the exception if check is false.
     * @throws IllegalArgumentException
     *             when check is false.
     */
    public static void requireArg(final boolean check, final String what)
    {
        if (!check)
        {
            throw new IllegalArgumentException("Pre Condition check failed: '"
                    + what + "'.");
        }
    }
}
