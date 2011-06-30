package org.ncibi.commons.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A cover class that encapsulates the Statement and ResultSet. Since both of
 * these items are used by JDBC callers and they both need to be closed, this
 * class enscapsulates some of the details of access and provides a way to make
 * sure that the two objects are closed in the correct order and captures
 * exceptions thrown by one or both statements.
 * 
 * @author V. Glenn Tarcea
 * 
 */
public class JDBResultSet
{
    /**
     * A JDBC statement.
     */
    private final Statement statement;

    /**
     * A JDBC ResultSet.
     */
    private final ResultSet resultSet;

    /**
     * Constructor that takes both a statement and a resultset.
     * 
     * @param s
     *            The statement
     * @param rs
     *            The resultset
     */
    public JDBResultSet(final Statement s, final ResultSet rs)
    {
        this.statement = s;
        this.resultSet = rs;
    }

    /**
     * Accessor to the statement.
     * 
     * @return the statement.
     */
    public Statement getStatement()
    {
        return statement;
    }

    /**
     * Accessor to the ResultSet.
     * 
     * @return the resultset
     */
    public ResultSet getResultSet()
    {
        return resultSet;
    }

    /**
     * Ensures that the objects are closed in the proper order, testing for null
     * and capturing exceptions so that code doesn't need to embed the closes in
     * multiple try/catch blocks.
     */
    public void close()
    {
        if (resultSet != null)
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        if (statement != null)
        {
            try
            {
                statement.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
